package com.ll.hype.domain.order.buy.controller;

import com.ll.hype.domain.order.buy.dto.request.BuyConfirmRequest;
import com.ll.hype.domain.order.buy.dto.request.CreateBuyRequest;
import com.ll.hype.domain.order.buy.dto.response.BuyFormResponse;
import com.ll.hype.domain.order.buy.dto.response.BuyModifyPriceResponse;
import com.ll.hype.domain.order.buy.dto.response.BuyResponse;
import com.ll.hype.domain.order.buy.dto.response.BuySizeInfoResponse;
import com.ll.hype.domain.order.buy.service.BuyService;
import com.ll.hype.domain.order.order.dto.response.OrderResponse;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.global.security.authentication.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/buy")
@Controller
public class BuyController {
    private final BuyService buyService;

    /**
     * 사용 x
     */
    @GetMapping("/shoes/{shoesId}")
    public String viewOrderRequestPage(@PathVariable("shoesId") Long shoesId,
                                       Model model) {
        ShoesResponse findShoes = buyService.findByShoesId(shoesId);
        model.addAttribute("shoes", findShoes);
        return "/domain/order/order";
    }


    /**
     * 신발상세(구매입찰 진행) -> 사이즈 선택 페이지로
     */
    @PostMapping("/shoes")
    public String buyShoesPickSuccess(@RequestParam("id") Long id,
                                      @RequestParam("selectSize") int size,
                                      @AuthenticationPrincipal UserPrincipal user,
                                      Model model) {
        BuySizeInfoResponse findBySalesMinPrice = buyService.findByShoesSizeMinPriceAll(id, user.getMember(), size);
        model.addAttribute("shoes", findBySalesMinPrice);
        return "domain/order/buy/select_size";
    }

    /**
     * 사이즈 선택 완료 -> 구매 입찰 상세페이지
     */
    @PostMapping("/shoes/bid")
    public String buyBid(@RequestParam("shoesId") Long shoesId,
                         @RequestParam("size") int size,
                         @AuthenticationPrincipal UserPrincipal user,
                         CreateBuyRequest buyRequest,
                         Model model) {
        BuyFormResponse byShoesSizeMinPriceOne = buyService.findByShoesSizeMinPriceOne(shoesId, size, user.getMember());
        model.addAttribute("data", byShoesSizeMinPriceOne);
        return "domain/order/buy/bid_pricing";
    }

    /**
     * 구매 입찰 상세페이지 즉시 구매
     */
    @PostMapping("/shoes/now")
    public String buyNow(@RequestParam("shoesId") Long shoesId,
                         @RequestParam("size") int size,
                         @AuthenticationPrincipal UserPrincipal user,
                         CreateBuyRequest buyRequest,
                         Model model) {
        BuyFormResponse byShoesSizeMinPriceOne = buyService.findByShoesSizeMinPriceOne(shoesId, size, user.getMember());
        model.addAttribute("data", byShoesSizeMinPriceOne);
        return "domain/order/buy/now_pricing";
    }

    /**
     * 구매 입찰 상세페이지 -> 구매 입찰 객체 생성
     */
    @PostMapping("/shoes/buy/bid")
    public String createBuyBid(CreateBuyRequest buyRequest,
                               @AuthenticationPrincipal UserPrincipal user) {
        buyService.createBuyBid(buyRequest, user.getMember());
        return "redirect:/mypage/buy/history";
    }

    /**
     * 구매 입찰 상세페이지 내 즉시 구매 기능
     */
    @PostMapping("/shoes/buy/now")
    public String createBuyNow(CreateBuyRequest buyRequest,
                               @AuthenticationPrincipal UserPrincipal user,
                               Model model) {
        OrderResponse order = buyService.createBuyNow(buyRequest, user.getMember());
        model.addAttribute("order", order);
        return "domain/order/order/order_payment";
    }

    /**
     * 신발 사이즈 선택 시 이미 등록한 입찰 상품인지 확인하는 기능
     */
    @PostMapping("/confirm")
    public ResponseEntity<String> confirmBuy(@RequestBody BuyConfirmRequest buyRequest,
                                             @AuthenticationPrincipal UserPrincipal user) {
        if (!buyService.confirmBuy(user.getMember(), buyRequest.getShoesId(), buyRequest.getSize())) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("이미 입찰 중인 신발 및 사이즈 입니다.");
        }

        return ResponseEntity.ok().body("입찰 가능");
    }

    /**
     * Buy 금액 수정 폼 이동
     */
    @PostMapping("/modify")
    public String buyModifyForm(@RequestParam("id") Long id,
                                @AuthenticationPrincipal UserPrincipal user,
                                Model model) {
        BuyModifyPriceResponse buyIdAndMember = buyService.findBuyIdAndMember(id, user.getMember());
        model.addAttribute("data", buyIdAndMember);
        return "domain/order/buy/bid_modify_price_form";
    }

    /**
     * Buy 금액 수정
     */
    @PutMapping("/modify")
    public String buyModify(@RequestParam("id") Long id,
                            @RequestParam("price") Long price,
                            @AuthenticationPrincipal UserPrincipal user) {
        buyService.updatePrice(id, price, user.getMember());
        return "redirect:/mypage/buy/history";
    }

    /**
     * Buy 입찰 금액 수정 페이지에서 즉시 구매
     */
    @PostMapping("/modify/now")
    public String buyModifyNow(@RequestParam("id") Long id,
                               @RequestParam("nowPrice") Long nowPrice,
                               @AuthenticationPrincipal UserPrincipal user,
                               Model model) {
        OrderResponse modifyBuyNow = buyService.createModifyBuyNow(id, nowPrice, user.getMember());
        model.addAttribute("order", modifyBuyNow);
        return "domain/order/order/order_payment";
    }

    /**
     * Buy 삭제
     */
    @DeleteMapping("/delete")
    public String buyDelete(@RequestParam("id") Long id,
                            @AuthenticationPrincipal UserPrincipal user) {
        log.info("[BuyController.deleteBuy] id : " + id);
        buyService.deleteBuy(id, user.getMember());
        return "redirect:/mypage/buy/history";
    }
}