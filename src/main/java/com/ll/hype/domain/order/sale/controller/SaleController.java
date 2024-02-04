package com.ll.hype.domain.order.sale.controller;

import com.ll.hype.domain.order.buy.dto.BuyResponse;
import com.ll.hype.domain.order.buy.dto.BuySizeInfoResponse;
import com.ll.hype.domain.order.buy.dto.CreateBuyRequest;
import com.ll.hype.domain.order.buy.service.BuyService;
import com.ll.hype.domain.order.sale.dto.CreateSaleRequest;
import com.ll.hype.domain.order.sale.dto.SaleRequest;
import com.ll.hype.domain.order.sale.dto.SaleResponse;
import com.ll.hype.domain.order.sale.dto.SaleSizeInfoResponse;
import com.ll.hype.domain.order.sale.service.SaleService;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.dto.ShoesSizeDTO;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.domain.shoes.shoes.service.ShoesService;
import com.ll.hype.global.security.authentication.UserPrincipal;
import com.ll.hype.global.util.ShoesSizeGenerator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@RequestMapping("/sale")
@Controller
public class SaleController {

    private final BuyService buyService;
    private final SaleService saleService;

    // TODO
    // 신발리스트, 상세(1~3장) / 약관 동의, 입찰, 즉시거래, 거래체결 이미지 추가
    // /shoes/approve 즉시 구매가(판매 입찰 중 선택 사이즈의 가장 낮은 가격), 즉시 판매가(구매 입찰 중 선택 사이즈의 가장 높은 가격)

    // 신발상세 -> 사이즈 선택
    @PostMapping("/shoes")
    public String saleShoesPickSuccess(@RequestParam("shoesId") long shoesId,
                                       Model model) {
        SaleSizeInfoResponse findByBuyMaxPrice = saleService.findByShoesSizeMaxPrice(shoesId);
        model.addAttribute("shoes", findByBuyMaxPrice);

        return "domain/order/sale/selectSize";
    }

    // 사이즈 선택 -> 약관 동의
    @PostMapping("/shoes/size")
    public String saleSizePickSuccess(@RequestParam("shoesId") long shoesId,
                                      @RequestParam("size") int size,
                                      Model model) {
        // TODO
        // 이미지, 모델번호, 영문,한글명, 선택사이즈
        ShoesResponse shoes = saleService.findByShoesId(shoesId);
        model.addAttribute("shoes", shoes);

        model.addAttribute("shoesId", shoesId);
        model.addAttribute("size", size);

        return "domain/order/sale/approve";
    }

    // 약관 동의 -> 판매가 결정
    @PostMapping("/shoes/approve")
    public String saleApproveSuccess(@RequestParam("shoesId") long shoesId,
                                     @RequestParam("size") int size,
                                     Model model) {
        // TODO
        // 판매 입찰 최소가격 설정 (즉시 판매가 보다 높은 가격)

        model.addAttribute("shoesId", shoesId);
        model.addAttribute("size", size);

        //즉시 구매가
        BuySizeInfoResponse findBySaleMinPrice = buyService.findByShoesSizeMinPrice(shoesId);
        model.addAttribute("saleShoes", findBySaleMinPrice);
        //즉시 판매가
        SaleSizeInfoResponse findByBuyMaxPrice = saleService.findByShoesSizeMaxPrice(shoesId);
        model.addAttribute("buyShoes", findByBuyMaxPrice);

        return "domain/order/sale/pricing";
    }

    // 판매가 결정 -> 판매 입찰 생성
    @PostMapping("/shoes/sale/create")
    public String createSale(CreateSaleRequest saleRequest,
                             @AuthenticationPrincipal UserPrincipal user,
                             RedirectAttributes redirectAttributes) {

        saleService.createSale(saleRequest, user.getMember());
        SaleResponse saleResponse = saleService.createSale(saleRequest, user.getMember());
        redirectAttributes.addFlashAttribute("saleId", saleResponse.getId());

        return "redirect:/sale/shoes/sale/detail";
    }

    // 판매 입찰 내역
    @GetMapping("/shoes/sale/detail")
    public String saleDetail(@ModelAttribute("saleId")Long saleId, Model model) {

        SaleResponse saleResponse = saleService.findById(saleId);
        model.addAttribute("saleResponse", saleResponse);

        return "domain/order/sale/saleDetail";
    }
}