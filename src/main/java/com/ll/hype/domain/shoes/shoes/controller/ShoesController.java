package com.ll.hype.domain.shoes.shoes.controller;

import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.entity.ShoesCategory;
import com.ll.hype.domain.shoes.shoes.service.ShoesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/shoes")
@Controller
public class ShoesController {
    private final ShoesService shoesService;

    // TODO
    // 1. 랭킹: 조회수 + 1 관심 + 10
    // 2. 사이즈등록 O: 상세페이지에 본인 사이즈 → 내 사이즈의 즉시 구매, 판매가
    //    사이즈등록 X: 모든 사이즈 보기 → 최저 즉시 구매가, 최고 즉시 판매가
    // 3. 최근 거래가

    //신발 상세
    @GetMapping("/{id}")
    public String shoesDetail(@PathVariable("id") long id, Model model){
        ShoesResponse findOne = shoesService.findById(id);
        model.addAttribute("shoes", findOne);

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
}
