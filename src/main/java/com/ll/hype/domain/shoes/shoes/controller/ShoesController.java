package com.ll.hype.domain.shoes.shoes.controller;

import com.ll.hype.domain.shoes.shoes.dto.ShoesAddWIshRequest;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.dto.ShoesWishCheckRequest;
import com.ll.hype.domain.shoes.shoes.service.ShoesService;
import com.ll.hype.domain.wishlist.wishlist.service.WishlistService;
import com.ll.hype.global.security.authentication.UserPrincipal;
import java.util.List;
import java.util.Optional;
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

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/shoes")
@Controller
public class ShoesController {
    private final ShoesService shoesService;
    private final WishlistService wishlistService;

    //신발 상세
    @GetMapping("/{id}")
    public String shoesDetail(@PathVariable("id") long id, Model model) {
        ShoesResponse findOne = shoesService.findById(id);
        model.addAttribute("shoes", findOne);

//        Optional<OrderPriceResponse> recentOrder = shoesService.getLatestTradePrice(id);
//        recentOrder.ifPresent(order -> model.addAttribute("order", order));

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
    public ResponseEntity<String> wish(@RequestBody ShoesAddWIshRequest request,
                                       @AuthenticationPrincipal UserPrincipal user) {
        log.info("[ShoesController.wish] ShoesAddWIshRequest : " + request.getShoesId() + " / " + request.getSize());
        boolean check = wishlistService.addWishShoes(request.getShoesId(), request.getSize(), user.getMember());

        log.info("[ShoesController.wish] check : " + check);

        if (!check) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("알 수 없는 오류 발생");
        }
        return ResponseEntity.ok().body("");
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
