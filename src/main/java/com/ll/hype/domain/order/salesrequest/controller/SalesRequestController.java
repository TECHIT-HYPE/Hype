package com.ll.hype.domain.order.salesrequest.controller;

import com.ll.hype.domain.order.orderrequest.dto.OrderReqResponse;
import com.ll.hype.domain.order.orderrequest.service.OrderRequestService;
import com.ll.hype.domain.order.salesrequest.dto.SalesReqRequest;
import com.ll.hype.domain.order.salesrequest.dto.SalesReqResponse;
import com.ll.hype.domain.order.salesrequest.service.SalesRequestService;
import com.ll.hype.domain.shoes.shoes.dto.ShoesRequest;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.dto.ShoesSizeDTO;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.domain.shoes.shoes.service.ShoesService;
import com.ll.hype.global.util.ShoesSizeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/sale")
@Controller
public class SalesRequestController {

    private final ShoesService shoesService;
    private final OrderRequestService orderRequestService;
    private final SalesRequestService salesRequestService;

    //사이즈 선택
    //신발고유번호, 이름, 이미지, 사이즈 노출
    @GetMapping("/select/{id}")
    public String selectSizeForm(@PathVariable("id") Long id, Model model) {
        ShoesResponse shoesResponse = shoesService.findById(id);
        model.addAttribute("shoes", shoesResponse);

        List<OrderReqResponse> orderRequestList = orderRequestService.findByShoesId(id);
        model.addAttribute("orderRequest", orderRequestList);

        List<ShoesSizeDTO> list = ShoesSizeGenerator.getSizes().stream()
                .map(size -> {

                    for (OrderReqResponse order : orderRequestList) {
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
//        OrderReqResponse orderReqResponse = orderRequestService.findById(id);
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
    public String approveForm(SalesReqRequest salesReqRequest) {

        return "domain/salesRequest/salesRequest/approve";
    }
    //판매 약관 동의 처리
    @PostMapping("/approval")
    public String approve(SalesReqRequest salesReqRequest) {

        return "domain/selectSize";
    }

    //신발 정보 및 예상 정산 금액 표시, 판매 입찰 생성 기능


    //판매 결정 및 정보 입력
    @PostMapping("select/{id}/{shoesSize}")
    public String saveSalesRequest (SalesReqRequest salesReqRequest, Model model) {
        salesRequestService.saveSalesRequest(salesReqRequest);
        return "redirect:/";
    }
}
