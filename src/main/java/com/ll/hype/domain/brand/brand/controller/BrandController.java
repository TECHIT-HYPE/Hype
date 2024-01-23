package com.ll.hype.domain.brand.brand.controller;

import com.ll.hype.domain.brand.brand.dto.BrandResponse;
import com.ll.hype.domain.brand.brand.service.BrandService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/brand")
public class BrandController {
    private final BrandService brandService;

    // 관리자 브랜드 전체 조회 (ENABLE 조회 조회)
    @GetMapping("/brand/list")
    public String findAll(Model model) {
        List<BrandResponse> brands = brandService.findAll();
        model.addAttribute("brands", brands);
        return "domain/brand/brand/list";
    }
}
