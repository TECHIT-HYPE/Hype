package com.ll.hype.domain.shoes.shoes.controller;

import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.service.ShoesService;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/shoes")
@Controller
public class ShoesController {
    private final ShoesService shoesService;

    //신발 상세
    @GetMapping("/{id}")
    public String shoesDetail(@PathVariable("id") long id, Model model){
        ShoesResponse findOne = shoesService.findById(id);
        model.addAttribute("shoes", findOne);

        //즉시 구매/판매가 최근 거래가
        return "domain/shoes/shoes/detail";
    }

    // 신발 전체 목록
    @GetMapping("/list")
    public String findAll(Model model) {
        List<ShoesResponse> shoesList = shoesService.findAll();
        model.addAttribute("shoesList", shoesList);
        return "domain/shoes/shoes/list";
    }
}
