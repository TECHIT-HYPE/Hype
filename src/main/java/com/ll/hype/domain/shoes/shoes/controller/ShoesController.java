package com.ll.hype.domain.shoes.shoes.controller;

import com.ll.hype.domain.shoes.shoes.dto.ShoesRequest;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.service.ShoesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/shoes")
public class ShoesController {

    private final ShoesService shoesService;

    //신발 상세
    @GetMapping("/{id}")
    public String shoesDetail(@PathVariable long id, Model model){
        ShoesResponse findOne = shoesService.findById(id);
        model.addAttribute("shoes", findOne);
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
