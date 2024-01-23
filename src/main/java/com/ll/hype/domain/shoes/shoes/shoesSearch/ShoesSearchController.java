package com.ll.hype.domain.shoes.shoes.shoesSearch;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/shoes")
@Controller
public class ShoesSearchController {
    private final ShoesSearchService shoesSearchService;

    @GetMapping("/search")
    public String shesSearchForm() {
        return "domain/shoes/searchForm";
    }

    @PostMapping("/search")
    public String shoesSearch(@RequestParam(value = "keyword") String keyword,Model model) {
        List<ShoesResponse> byKeyword = shoesSearchService.findByword(keyword);
        model.addAttribute("shoes", byKeyword);
        return "domain/shoes/list";
    }

}
