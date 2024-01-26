package com.ll.hype.domain.order.buy.controller;

import com.ll.hype.domain.order.buy.service.BuyService;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Slf4j
@RequiredArgsConstructor
@RequestMapping("/order")
@Controller
public class BuyController {

    private final BuyService buyService;

    @GetMapping("/shoes/{shoesId}")
    public String viewOrderRequestPage(
            @PathVariable Long shoesId,
            Model model
    ) {

        ShoesResponse findShoes = buyService.findByShoesId(shoesId);
        model.addAttribute("shoes", findShoes);

        return "/domain/order/order";
    }

}