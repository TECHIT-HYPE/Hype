package com.ll.hype.domain.shoes.shoes.controller;

import com.ll.hype.domain.order.order.dto.response.OrderPriceResponse;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.dto.ShoesWishCheckRequest;
import com.ll.hype.domain.shoes.shoes.service.ShoesService;
import com.ll.hype.domain.wishlist.wishlist.service.WishlistService;
import com.ll.hype.global.security.authentication.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/shoes")
@Controller
public class ShoesController {
    private final ShoesService shoesService;
    private final WishlistService wishlistService;

    // TODO
    // 1. 랭킹: 조회수 + 1 관심 + 10
    // 2. 사이즈등록 O: 상세페이지에 본인 사이즈 → 내 사이즈의 즉시 구매, 판매가
    //    사이즈등록 X: 모든 사이즈 보기 → 최저 즉시 구매가, 최고 즉시 판매가

    //신발 상세
    @GetMapping("/{id}")
    public String shoesDetail(@PathVariable("id") long id, Model model){
        ShoesResponse findOne = shoesService.findById(id);
        model.addAttribute("shoes", findOne);

//        OrderPriceResponse recentOrder = shoesService.getLatestTradePrice(id);
//        model.addAttribute("order", recentOrder);
        Optional<OrderPriceResponse> recentOrder = shoesService.getLatestTradePrice(id);
        recentOrder.ifPresent(order -> model.addAttribute("order", order));

        return "domain/shoes/shoes/detail";
    }

    //신발 전체 목록
    @GetMapping("/list")
    public String findAll(@RequestParam(required = false) List<String> categories, Model model) {
        List<ShoesResponse> shoesList = shoesService.getShoesList(Optional.ofNullable(categories));

        model.addAttribute("selectedCategories", categories);
        model.addAttribute("shoesList", shoesList);
        return "domain/shoes/shoes/list";
    }

    @PostMapping("/love")
    public String wish(@RequestParam("id") Long id,
                       @RequestParam("selectSize") int selectSize,
                       @AuthenticationPrincipal UserPrincipal user) {
        wishlistService.addWishShoes(id, selectSize, user.getMember());
        return "redirect:/shoes/%s".formatted(id);
    }

    @PostMapping("/love/check")
    public ResponseEntity<String> checkWish(@RequestBody ShoesWishCheckRequest shoesWishCheckRequest,
                                            @AuthenticationPrincipal UserPrincipal user) {

        log.info("[ShoesController.checkWish] id : " + shoesWishCheckRequest.getShoesId());
        log.info("[ShoesController.checkWish] size : " + shoesWishCheckRequest.getSize());
        if (!wishlistService.checkShoesWish(shoesWishCheckRequest, user.getMember())) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("이미 찜한 상품입니다..");
        }
        return ResponseEntity.ok().body("찜 가능");
    }
}
