package com.ll.hype.domain.admin.admin.controller;

import com.ll.hype.domain.admin.admin.dto.request.MemberModifyRequest;
import com.ll.hype.domain.admin.admin.dto.request.MemberPwClearRequest;
import com.ll.hype.domain.admin.admin.dto.response.MemberListResponse;
import com.ll.hype.domain.admin.admin.service.AdminService;
import com.ll.hype.domain.brand.brand.dto.BrandRequest;
import com.ll.hype.domain.brand.brand.dto.BrandResponse;
import com.ll.hype.domain.customer.question.dto.QuestionResponse;
import com.ll.hype.domain.order.buy.dto.response.BuyResponse;
import com.ll.hype.domain.shoes.shoes.dto.ShoesRequest;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;


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

    //============== Member Start ==============
    @GetMapping("/member/main")
    public String memberHome() {
        return "domain/admin/member/main";
    }

    @GetMapping("/member/list")
    public String memberList(Model model) {
        List<MemberListResponse> members = adminService.getMembers();
        model.addAttribute("data", members);
        return "domain/admin/member/list";
    }

    @GetMapping("/member/{id}")
    public String memberDetail(@PathVariable("id") Long id,
                               MemberModifyRequest memberModifyRequest,
                               Model model) {
        MemberListResponse member = adminService.getMember(id);
        model.addAttribute("data", member);
        return "domain/admin/member/detail";
    }

    @PostMapping("/member/clearPw")
    public ResponseEntity<String> clearPw(@RequestBody MemberPwClearRequest request) {
        log.info("[AdminController.memberPwClear] id : " + request.getId());
        adminService.clearMemberPw(request.getId());
        return ResponseEntity.ok().body("비밀번호 변경 완료");
    }

    @PutMapping("/member/modify")
    public String memberModify(MemberModifyRequest memberModifyRequest) {
        adminService.modifyMember(memberModifyRequest);
        return "redirect:/admin/member/%s".formatted(memberModifyRequest.getId());
    }

    @DeleteMapping("/member/delete")
    public String memberDelete(@RequestParam("id") Long id) {
        log.info("[AdminController.memberDelete] id : " + id);
        adminService.memberDelete(id);
        return "redirect:/admin/member/list";
    }
    //============== Member End ==============


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
    public String createBrand(BrandRequest brandRequest,
                              @RequestParam(value = "files") List<MultipartFile> files) {
        adminService.saveBrand(brandRequest, files);
        return "redirect:/admin/brand/list";
    }

    // 관리자 브랜드 전체 조회 (DISABLE 까지 조회)
    @GetMapping("/brand/list")
    public String brandFindAll(Model model) {
        List<BrandResponse> brands = adminService.brandFindAll();
        model.addAttribute("brands", brands);
        return "domain/admin/brand/list";
    }

    @GetMapping("/brand/{id}")
    public String brandDetail(@PathVariable("id") Long id,
                              BrandRequest brandRequest,
                              Model model) {
        BrandResponse brand = adminService.brandFind(id);
        model.addAttribute("data", brand);
        return "domain/admin/brand/detail";
    }

    @DeleteMapping("/brand/delete")
    public String brandDelete(@RequestParam("id") Long id) {
        adminService.brandDelete(id);
        return "redirect:/admin/brand/list";
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
    public String createShoes(ShoesRequest shoesRequest,
                              @RequestParam("sizes") List<Integer> sizes,
                              @RequestParam(value = "files") List<MultipartFile> files) {
        adminService.saveShoes(shoesRequest, sizes, files);
        return "redirect:/admin/shoes/list";
    }

    @GetMapping("/shoes/list")
    public String shoesFindAll(Model model) {
        List<ShoesResponse> shoes = adminService.shoesFindAll();
        model.addAttribute("shoes", shoes);
        return "domain/admin/shoes/list";
    }
    //============== Shoes End ==============


    //============== CS Question & Answer Start ==============
    // Question Detail
    @GetMapping("/cs/question/detail/{id}")
    public String questionDetail(@PathVariable Long id,
                                 Model model) {
        QuestionResponse question = adminService.findQuestion(id);
        model.addAttribute("question", question);
        return "domain/admin/question/detail";
    }

    // Question All List
    @GetMapping("/cs/question/list")
    public String questionFindAll(Model model) {
        List<QuestionResponse> findAll = adminService.findQuestionAll();
        model.addAttribute("questions", findAll);
        return "domain/admin/question/list";
    }

    // Answer Create
    @PostMapping("/cs/qeestion/answer/create/{id}")
    public String answerCreate(@PathVariable("id") Long id,
                               @RequestParam("content") String content,
                               Model model,
                               Principal principal) {
        String email = principal.getName();
        adminService.createAnswer(id, content, email);

        QuestionResponse question = adminService.findQuestion(id);
        model.addAttribute("question", question);
        return "redirect:/admin/cs/question/detail/%s".formatted(id);
    }

    // Answer Delete
    @PostMapping("/cs/qeestion/answer/delete/{id}")
    public String answerDelete(@PathVariable("id") Long id) {
        QuestionResponse customerQResponse = adminService.deleteAnswer(id);
        return "redirect:/admin/cs/question/detail/%s".formatted(customerQResponse.getId());
    }
    //============== CS Question & Answer End ==============


    //============== Buy Start ==============
    @GetMapping("/buy/list")
    public String buyFindAll(Model model) {
        List<BuyResponse> buyResponses = adminService.buyFindAll();
        model.addAttribute("data", buyResponses);
        return "domain/admin/buy/list";
    }

    @DeleteMapping("/buy/delete")
    public String deleteBuy(@RequestParam("id") Long id) {
        adminService.buyDelete(id);
        return "redirect:/admin/buy/list";
    }
    //============== Buy End ==============
}
