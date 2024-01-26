package com.ll.hype.domain.order.sale.controller;

import com.ll.hype.domain.order.buy.dto.BuyResponse;
import com.ll.hype.domain.order.buy.service.BuyService;
import com.ll.hype.domain.order.sale.dto.SaleRequest;
import com.ll.hype.domain.order.sale.service.SaleService;
import com.ll.hype.domain.shoes.shoes.dto.ShoesRequest;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.dto.ShoesSizeDTO;
import com.ll.hype.domain.shoes.shoes.service.ShoesService;
import com.ll.hype.global.util.ShoesSizeGenerator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RequestMapping("/sale")
@Controller
public class SaleController {

    private final ShoesService shoesService;
    private final BuyService buyService;
    private final SaleService saleService;

    //사이즈 선택
    //신발고유번호, 이름, 이미지, 사이즈 노출
    @GetMapping("/select/{id}")
    public String selectSizeForm(@PathVariable("id") Long id, Model model) {
        ShoesResponse shoesResponse = shoesService.findById(id);
        model.addAttribute("shoes", shoesResponse);

        List<BuyResponse> orderRequestList = buyService.findByShoes(id);

        model.addAttribute("orderRequest", orderRequestList);

        List<ShoesSizeDTO> list = ShoesSizeGenerator.getSizes().stream()
                .map(size -> {

                    for (BuyResponse order : orderRequestList) {
                        if (order.getShoesSize().getSize() == size) {
                            return ShoesSizeDTO.builder()
                                    .size(size)
                                    .price(order.getPrice())
                                    .exists(true)
                                    .build();
                        }
                    }

                    return ShoesSizeDTO.builder()
                            .exists(false)
                            .size(size)
                            .build();
                })
                .toList();
        model.addAttribute("shoesSizeList", list);
        return "domain/salesRequest/salesRequest/selectSize";

//        ShoesResponse findOne = shoesService.findById(id);
//        model.addAttribute("shoes", findOne);
//        BuyResponse orderReqResponse = orderRequestService.findById(id);
//        model.addAttribute("orderRequest", orderReqResponse);
//        model.addAttribute("shoesSizeList", ShoesSizeGenerator.getSizes());
//        return "domain/salesRequest/salesRequest/selectSize";
    }

    //사이즈 선택 처리
    @PostMapping("/select/{id}")// 신발 id를 가져와야 하는데 오더리퀘스트아이디를
    public String selectSize(@RequestParam("id") long id, ShoesRequest shoesRequest) {
        return "redirect:/sale/approval";
    }
    //판매 약관 동의
    @GetMapping("/approval")
    public String approveForm(SaleRequest salesReqRequest) {

        return "domain/salesRequest/salesRequest/approve";
    }
    //판매 약관 동의 처리
    @PostMapping("/approval")
    public String approve(SaleRequest salesReqRequest) {

        return "domain/selectSize";
    }

    //신발 정보 및 예상 정산 금액 표시, 판매 입찰 생성 기능


    //판매 결정 및 정보 입력
    @PostMapping("select/{id}/{shoesSize}")
    public String saveSalesRequest (SaleRequest salesReqRequest, Model model) {
        saleService.saveSalesRequest(salesReqRequest);
        return "redirect:/";
    }
}
