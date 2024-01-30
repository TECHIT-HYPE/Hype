package com.ll.hype.domain.order.sale.controller;

import com.ll.hype.domain.order.buy.dto.BuyResponse;
import com.ll.hype.domain.order.buy.service.BuyService;
import com.ll.hype.domain.order.sale.dto.SaleRequest;
import com.ll.hype.domain.order.sale.service.SaleService;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.dto.ShoesSizeDTO;
import com.ll.hype.domain.shoes.shoes.service.ShoesService;
import com.ll.hype.global.util.ShoesSizeGenerator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@RequestMapping("/sale")
@Controller
public class SaleController {

    private final ShoesService shoesService;
    private final BuyService buyService;
    private final SaleService saleService;

    //사이즈 선택
    //신발고유번호, 이름, 이미지, 사이즈 노출
    @GetMapping("/select/{id}")
    public String selectSizeForm(@PathVariable("id") long id, Model model) {
        ShoesResponse shoesResponse = shoesService.findById(id);
        model.addAttribute("shoes", shoesResponse);

        Map<Integer, Optional<BuyResponse>> highestBidBySize = buyService.getHighestBidsGroupedBySize(id);
        //사이즈가 존재하면 가격 노출, 존재하지않으면 판매 입찰
        List<ShoesSizeDTO> list = ShoesSizeGenerator.getSizes().stream()
                .map(size -> highestBidBySize.getOrDefault(size, Optional.empty())
                        .map(buyResponse -> ShoesSizeDTO.builder()
                                .size(size)
                                .price(buyResponse.getPrice())
                                .exists(true)
                                .build())
                        .orElseGet(() -> ShoesSizeDTO.builder()
                                .exists(false)
                                .size(size)
                                .build()))
                .toList();

        model.addAttribute("shoesSizeList", list);

        return "domain/salesRequest/salesRequest/selectSize";
    }

    //사이즈 선택 처리
    @PostMapping("/select/{id}")
    public String selectSize(@PathVariable("id") Long id,
                             @RequestParam("selectedSize") int selectedSize,
                             RedirectAttributes redirectAttributes) {

        Optional<BuyResponse> highestBid = buyService.getHighestBidsGroupedBySize(id).get(selectedSize);

        highestBid.ifPresent(bid -> {
            redirectAttributes.addFlashAttribute("selectedSize", selectedSize);
            redirectAttributes.addFlashAttribute("highestBidId", bid.getId());
        });

        return "redirect:/sale/approval";
    }

    //판매 약관 동의
    @GetMapping("/approval")
    public String approveForm(@ModelAttribute("selectedSize") int selectedSize,
                              @ModelAttribute("highestBidId") long highestBidId,
                              Model model) {

        BuyResponse buyResponse = buyService.findById(highestBidId);
        model.addAttribute("buyResponse", buyResponse);

        return "domain/salesRequest/salesRequest/approve";
    }

    //판매 약관 동의 처리
    @PostMapping("/approval")
    public String approve() {

        return "redirect:/sale/pricing";
    }

    //신발 정보 및 예상 정산 금액 표시, 판매 입찰 생성 기능
    @GetMapping("/pricing")
    public String pricing(BuyResponse buyResponse, Model model) {
        model.addAttribute("buyResponse", buyResponse);
        return "domain/salesRequest/salesRequest/pricing";
    }


    //판매 결정 및 정보 입력
    @PostMapping("select/{id}/{shoesSize}")
    public String saveSalesRequest (SaleRequest saleRequest, Model model) {
        saleService.saveSalesRequest(saleRequest);
        return "redirect:/";
    }
}
