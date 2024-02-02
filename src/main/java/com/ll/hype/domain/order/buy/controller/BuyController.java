package com.ll.hype.domain.order.buy.controller;

import com.ll.hype.domain.order.buy.dto.BuySizeInfoResponse;
import com.ll.hype.domain.order.buy.dto.CreateBuyRequest;
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
        BuySizeInfoResponse findBySalesMinPrice = buyService.findByShoesSizeMinPrice(shoesId);
        model.addAttribute("shoes", findBySalesMinPrice);
        return "domain/order/buy/selectSize";
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

    @PostMapping("/shoes/approve")
    public String buyApproveSuccess(@RequestParam("shoesId") Long shoesId,
                                    @RequestParam("size") int size,
                                    CreateBuyRequest buyRequest,
                                    Model model) {
        // TODO
        // 여기에 뭐가 들어가야할까..
        // shoes 정보가 들어가야한다.
        // 선택한 size의 가장 낮은 가격의 매물 정보를 가져와야한다. 없으면 0
        model.addAttribute("shoesId", shoesId);
        model.addAttribute("size", size);
        return "domain/order/buy/pricing";
    }

    @PostMapping("/shoes/buy/create")
    public String createBuy(CreateBuyRequest buyRequest,
                            @AuthenticationPrincipal UserPrincipal user,
                            Model model) {
        log.info("[BuyController.createBuy] shoesId : " + buyRequest.getShoesId());
        log.info("[BuyController.createBuy] size : " + buyRequest.getSize());
        log.info("[BuyController.createBuy] price : " + buyRequest.getPrice());
        log.info("[BuyController.createBuy] endDate : " + buyRequest.getEndDate());
        log.info("[BuyController.createBuy] user : " + user.getMember().getId());

        buyService.createBuy(buyRequest, user.getMember());

        return "redirect:/";
    }
}