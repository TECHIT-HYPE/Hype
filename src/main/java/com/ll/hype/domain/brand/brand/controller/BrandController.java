package com.ll.hype.domain.brand.brand.controller;

import com.ll.hype.domain.brand.brand.dto.BrandRequest;
import com.ll.hype.domain.brand.brand.dto.BrandResponse;
import com.ll.hype.domain.brand.brand.service.BrandService;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/brand")
public class BrandController {
    private final BrandService brandService;

    // 브랜드 생성 폼
    @GetMapping("/create")
    public String createForm(BrandRequest brandRequest) {
        return "/domain/brand/brand/addForm";
    }

    // 브랜드 생성
    @PostMapping("/create")
    public String create(BrandRequest brandRequest) {
        brandService.save(brandRequest);
        return "redirect:/brand/list";
    }

    @GetMapping("/list")
    public String findAll(Model model) {
        List<BrandResponse> brands = brandService.findAll();
        model.addAttribute("brands", brands);
        return "domain/brand/brand/list";
    }
}
