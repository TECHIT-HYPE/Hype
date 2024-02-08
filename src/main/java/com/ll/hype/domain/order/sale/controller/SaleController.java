package com.ll.hype.domain.order.sale.controller;

import com.ll.hype.domain.order.buy.dto.request.BuyConfirmRequest;
import com.ll.hype.domain.order.buy.dto.response.BuyFormResponse;
import com.ll.hype.domain.order.buy.service.BuyService;
import com.ll.hype.domain.order.order.dto.response.OrderResponse;
import com.ll.hype.domain.order.sale.dto.request.CreateSaleRequest;
import com.ll.hype.domain.order.sale.dto.request.SaleConfirmRequest;
import com.ll.hype.domain.order.sale.dto.response.SaleFormResponse;
import com.ll.hype.domain.order.sale.dto.response.SaleResponse;
import com.ll.hype.domain.order.sale.dto.response.SaleSizeInfoResponse;
import com.ll.hype.domain.order.sale.service.SaleService;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.global.security.authentication.UserPrincipal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        ShoesResponse shoes = saleService.findByShoesId(shoesId);
        model.addAttribute("shoes", shoes);

        model.addAttribute("shoesId", shoesId);
        model.addAttribute("size", size);

        return "domain/order/sale/approve";
    }

    // 약관 동의 -> 판매 타입: 판매 입찰
    @PostMapping("/shoes/bid")
    public String saleBid(@RequestParam("shoesId") long shoesId,
                          @RequestParam("size") int size,
                          @AuthenticationPrincipal UserPrincipal user,

                          Model model) {

        //즉시 구매가 (최저 판매입찰가)
        BuyFormResponse byShoesSizeMinPriceOne = buyService.findByShoesSizeMinPriceOne(shoesId, size, user.getMember());
        model.addAttribute("BuyFormResponse", byShoesSizeMinPriceOne);

        //즉시 판매가 (최고 구매입찰가)
        SaleFormResponse byShoesSizeMaxPriceOne = saleService.findByShoesSizeMaxPriceOne(shoesId, size);
        model.addAttribute("SaleFormResponse", byShoesSizeMaxPriceOne);

        return "domain/order/sale/bidPricing";
    }
    // 약관 동의 -> 판매 타입: 즉시 판매
    @PostMapping("/shoes/now")
    public String saleNow(@RequestParam("shoesId") Long shoesId,
                          @RequestParam("size") int size,
                          @AuthenticationPrincipal UserPrincipal user,
                          Model model) {

        //즉시 구매가(최저 판매입찰가)
        BuyFormResponse byShoesSizeMinPriceOne = buyService.findByShoesSizeMinPriceOne(shoesId, size, user.getMember());
        model.addAttribute("BuyFormResponse", byShoesSizeMinPriceOne);

        //즉시 판매가(최고 구매입찰가)
        SaleFormResponse byShoesSizeMaxPriceOne = saleService.findByShoesSizeMaxPriceOne(shoesId, size);
        model.addAttribute("SaleFormResponse", byShoesSizeMaxPriceOne);

        return "domain/order/sale/nowPricing";
    }

    // 판매 생성: 판매 입찰
    @PostMapping("/shoes/sale/bid")
    public String createSaleBid(CreateSaleRequest saleRequest,
                                @AuthenticationPrincipal UserPrincipal user,
                                RedirectAttributes redirectAttributes) {

        SaleResponse saleResponse = saleService.createSaleBid(saleRequest, user.getMember());
        redirectAttributes.addFlashAttribute("saleId", saleResponse.getId());

        return "redirect:/sale/shoes/sale/bid/detail";
    }

    // 판매 생성: 즉시 판매
    @PostMapping("/shoes/sale/now")
    public String createSaleNow(CreateSaleRequest saleRequest,
                                @AuthenticationPrincipal UserPrincipal user,
                                Model model) {

        OrderResponse order = saleService.createSaleNow(saleRequest, user.getMember());
        model.addAttribute("order", order);
        return "redirect:/";
    }

    // 생성 완료: 판매 입찰 내역
    @GetMapping("/shoes/sale/bid/detail")
    public String saleBidDetail(@ModelAttribute("saleId")Long saleId, Model model) {

        SaleResponse saleResponse = saleService.findById(saleId);
        model.addAttribute("saleResponse", saleResponse);

        return "domain/order/sale/bidDetail";
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmSale(@AuthenticationPrincipal UserPrincipal user,
                                             @RequestBody SaleConfirmRequest saleRequest) {
        long shoesId = saleRequest.getShoesId();
        int size = saleRequest.getSize();

        if (!saleService.confirmSale(user.getMember(), shoesId, size)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("이미 입찰 중인 신발 및 사이즈 입니다.");
        }

        return ResponseEntity.ok().body("입찰 가능");
    }
}