package com.ll.hype.domain.order.buy.controller;

import com.ll.hype.domain.order.buy.dto.response.BuyFormResponse;
import com.ll.hype.domain.order.buy.dto.response.BuyResponse;
import com.ll.hype.domain.order.buy.dto.response.BuySizeInfoResponse;
import com.ll.hype.domain.order.buy.dto.request.CreateBuyRequest;
import com.ll.hype.domain.order.buy.service.BuyService;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.global.security.authentication.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/buy")
@Controller
public class BuyController {
    private final BuyService buyService;

    @GetMapping("/shoes/{shoesId}")
    public String viewOrderRequestPage(@PathVariable("shoesId") Long shoesId,
                                       Model model) {
        ShoesResponse findShoes = buyService.findByShoesId(shoesId);
        model.addAttribute("shoes", findShoes);
        return "/domain/order/order";
    }

    // 신발상세(구매입찰 진행) -> 사이즈 선택 페이지로
    @PostMapping("/shoes")
    public String buyShoesPickSuccess(@RequestParam("shoesId") Long shoesId,
                                      Model model) {
        BuySizeInfoResponse findBySalesMinPrice = buyService.findByShoesSizeMinPriceAll(shoesId);
        model.addAttribute("shoes", findBySalesMinPrice);
        return "domain/order/buy/select_size";
    }

    // 사이즈 선택 완료 -> 약관 동의 페이지로
    @PostMapping("/shoes/size")
    public String buySizePickSuccess(@RequestParam("shoesId") Long shoesId,
                                     @RequestParam("size") int size,
                                     Model model) {
        model.addAttribute("shoesId", shoesId);
        model.addAttribute("size", size);
        return "domain/order/buy/approve";
    }

    // 약관 동의 페이지 -> 구매 입찰 상세페이지
    @PostMapping("/shoes/bid")
    public String buyBid(@RequestParam("shoesId") Long shoesId,
                         @RequestParam("size") int size,
                         CreateBuyRequest buyRequest,
                         Model model) {

        BuyFormResponse byShoesSizeMinPriceOne = buyService.findByShoesSizeMinPriceOne(shoesId, size);
        model.addAttribute("data", byShoesSizeMinPriceOne);
        return "domain/order/buy/bid_pricing";
    }

    @PostMapping("/shoes/now")
    public String buyNow(@RequestParam("shoesId") Long shoesId,
                         @RequestParam("size") int size,
                         CreateBuyRequest buyRequest,
                         Model model) {

        BuyFormResponse byShoesSizeMinPriceOne = buyService.findByShoesSizeMinPriceOne(shoesId, size);
        long round = Math.round(byShoesSizeMinPriceOne.getSale().getPrice() * 0.05);
        model.addAttribute("data", byShoesSizeMinPriceOne);
        model.addAttribute("round", round);
        return "domain/order/buy/now_pricing";
    }

    // 구매 입찰 상세페이지 -> 구매 입찰 객체 생성
    @PostMapping("/shoes/buy/bid")
    public String createBuyBid(CreateBuyRequest buyRequest,
                               @AuthenticationPrincipal UserPrincipal user,
                               Model model) {
        BuyResponse buy = buyService.createBuy(buyRequest, user.getMember());
        return "redirect:/";
    }

    @PostMapping("/shoes/buy/now")
    public String createBuyNow(CreateBuyRequest buyRequest,
                               @AuthenticationPrincipal UserPrincipal user,
                               Model model) {
        BuyResponse buy = buyService.createBuy(buyRequest, user.getMember());
        return "redirect:/";
    }

    @GetMapping("test")
    public String test() {
        return "domain/order/order/ordertest";
    }
}