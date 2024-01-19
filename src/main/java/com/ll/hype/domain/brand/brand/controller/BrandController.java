package com.ll.hype.domain.brand.brand.controller;

import com.ll.hype.domain.brand.brand.dto.BrandRequest;
import com.ll.hype.domain.brand.brand.dto.BrandResponse;
import com.ll.hype.domain.brand.brand.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/brand")
public class BrandController {
    private final BrandService brandService;

    // 브랜드 생성 폼
    @GetMapping("/create")
    public String createForm(BrandRequest brandRequest) {
        return "/brand/addForm";
    }

    // 브랜드 생성
    @PostMapping("/create")
    public String create(BrandRequest brandRequest) {
        BrandResponse brand = brandService.save(brandRequest);
        log.info("Brand Name: " + brand.getKorName());
        return "/brand/addForm";
    }
}
