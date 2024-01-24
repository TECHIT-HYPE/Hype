package com.ll.hype.domain.shoes.shoes.shoessearch;

import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.dto.ShoesSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/shoes")
@Controller
public class ShoesSearchController {
    private final com.ll.hype.domain.shoes.shoes.shoessearch.ShoesSearchService shoesSearchService;

    @GetMapping("/search")
    public String shesSearchForm() {
        return "domain/shoes/shoessearch/searchForm";
    }

    @PostMapping("/search")
    public String shoesSearch(@RequestParam(value = "keyword") String keyword,Model model) {
        ShoesSearchResponse data = shoesSearchService.findByKeyword(keyword);
        model.addAttribute("data", data);
        return "domain/shoes/shoessearch/list";
    }
}

