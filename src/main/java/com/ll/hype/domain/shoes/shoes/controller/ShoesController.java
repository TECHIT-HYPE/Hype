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
        Optional<Shoes> shoes = shoesService.findById(id);
        model.addAttribute("shoes", shoes);
        return "domain/shoes/shoes/detail";
    }

    //신발 생성 - 관리자
    @GetMapping("/create")
    public String createForm(ShoesRequest shoesRequest) {
        return "/domain/shoes/shoes/addForm";
    }

    @PostMapping("/create")
    public String create(ShoesRequest shoesRequest) {
        shoesService.save(shoesRequest);
        return "redirect:/shoes/list";
    }

    //신발 리스트 - 관리자
    @GetMapping("/list")
    public String findAll(Model model) {
        List<ShoesResponse> shoesList = shoesService.findAll();
        model.addAttribute("shoesList", shoesList);
        return "domain/shoes/shoes/list";
    }

    //신발 수정 - 관리자
    //신발 삭제 - 관리자

}
