package com.ll.hype.domain.admin.admin.controller;

import com.ll.hype.domain.admin.admin.service.AdminService;
import com.ll.hype.domain.brand.brand.dto.BrandRequest;
import com.ll.hype.domain.brand.brand.dto.BrandResponse;
import com.ll.hype.domain.customer.question.dto.CustomerQResponse;
import com.ll.hype.domain.shoes.shoes.dto.ShoesRequest;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {
    private final AdminService adminService;

    //============== Main Start ==============
    // Admin home
    @GetMapping("/main")
    public String home() {
        return "domain/admin/main";
    }
    //============== Main End ==============


    //============== Brand Start ==============
    // Brand home
    @GetMapping("/main/brand")
    public String brandHome() {
        return "domain/admin/brand/main";
    }

    // 관리자 브랜드 생성 폼
    @GetMapping("/brand/create")
    public String createBrandForm(BrandRequest brandRequest) {
        return "/domain/admin/brand/addForm";
    }

    // 관리자 브랜드 생성
    @PostMapping("/brand/create")
    public String createBrand(BrandRequest brandRequest) {
        adminService.saveBrand(brandRequest);
        return "redirect:/admin/brand/list";
    }

    // 관리자 브랜드 전체 조회 (DISABLE 까지 조회)
    @GetMapping("/brand/list")
    public String brandFindAll(Model model) {
        List<BrandResponse> brands = adminService.brandFindAll();
        model.addAttribute("brands", brands);
        return "domain/admin/brand/list";
    }
    //============== Brand End ==============


    //============== Shoes Start ==============
    // Shoes home
    @GetMapping("/main/shoes")
    public String shoesHome() {
        return "domain/admin/shoes/main";
    }
    @GetMapping("/shoes/create")
    public String createShoesForm(ShoesRequest shoesRequest, Model model) {
        model.addAttribute("brands", adminService.brandFindEnable());
        return "/domain/admin/shoes/addForm";
    }

    @PostMapping("/shoes/create")
    public String createShoes(ShoesRequest shoesRequest) {
        adminService.saveShoes(shoesRequest);
        return "redirect:/admin/shoes/list";
    }

    @GetMapping("/shoes/list")
    public String shoesFindAll(Model model) {
        List<ShoesResponse> shoes = adminService.shoesFindAll();
        model.addAttribute("shoes", shoes);
        return "domain/admin/shoes/list";
    }
    //============== Shoes End ==============


    //============== CS Question Start ==============
    // Question Detail
    @GetMapping("/cs/question/detail/{id}")
    public String questionDetail(@PathVariable Long id, Model model) {
        CustomerQResponse question = adminService.findQuestion(id);
        model.addAttribute("question", question);
        return "domain/admin/question/detail";
    }

    // Question All List
    @GetMapping("/cs/question/list")
    public String questionFindAll(Model model) {
        List<CustomerQResponse> findAll = adminService.findQuestionAll();
        model.addAttribute("questions", findAll);
        return "domain/admin/question/list";
    }
    //============== CS Question End ==============
}
