package com.ll.hype.domain.order.sale.controller;

import com.ll.hype.domain.order.buy.dto.BuyResponse;
import com.ll.hype.domain.order.buy.service.BuyService;
import com.ll.hype.domain.order.sale.dto.SaleRequest;
import com.ll.hype.domain.order.sale.service.SaleService;
import com.ll.hype.domain.shoes.shoes.dto.ShoesRequest;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.dto.ShoesSizeDTO;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.domain.shoes.shoes.service.ShoesService;
import com.ll.hype.global.util.ShoesSizeGenerator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String selectSizeForm(@PathVariable("id") Long id,
                                 Model model,
                                 SaleRequest saleRequest) {
        ShoesResponse shoesResponse = shoesService.findById(id);
        model.addAttribute("shoes", shoesResponse);

        List<BuyResponse> orderRequestList = buyService.findByShoes(id);

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
//++각 신발 사이즈별로 가장 높은 가격의 구매 입찰을 우선시하고, 가격이 같은 경우 먼저 입찰한 순서대로
//++만료 기한
                    return ShoesSizeDTO.builder()
                            .exists(false)
                            .size(size)
                            .build();
                })
                .toList();
        model.addAttribute("shoesSizeList", list);
        return "domain/salesRequest/salesRequest/selectSize";
    }

    //사이즈 선택 처리
    @PostMapping("/select/{id}")// 신발 id를 가져와야 하는데 오더리퀘스트아이디를
    public String selectSize(@PathVariable("id") long id,
                             @RequestParam(value = "selectedSize", required = true) int selectedSize,
                             Model model) {
        System.out.println("[SaleController.selectSize] selectedSize : " +  selectedSize);
        BuyResponse buyResponse = buyService.findByShoesIdAndShoesSize(id, selectedSize);
        model.addAttribute("buyResponse", buyResponse);
        return "domain/salesRequest/salesRequest/approve";
//        return "redirect:/sale/approval";
    }

    //판매 약관 동의
//    @GetMapping("/approval")
//    public String approveForm(
//            @ModelAttribute("buyResponse") BuyResponse buyResponse,
//            //@RequestParam("buyResponse") BuyResponse buyResponse,
//                              Model model) {
//        System.out.println("[SaleController.approveForm] buyResponse.getId() : " +  buyResponse.getId());
//        System.out.println("[SaleController.approveForm] buyResponse.getShoesSize().getSize() : " +  buyResponse.getShoesSize());
//        model.addAttribute("buyResponse", buyResponse);
//        return "domain/salesRequest/salesRequest/approve";
//    }

    //판매 약관 동의 처리
    @PostMapping("/approval")
    public String approve() {

        return "domain/salesRequest/salesRequest/pricing";
    }

    //신발 정보 및 예상 정산 금액 표시, 판매 입찰 생성 기능


    //판매 결정 및 정보 입력
    @PostMapping("select/{id}/{shoesSize}")
    public String saveSalesRequest (SaleRequest saleRequest, Model model) {
        saleService.saveSalesRequest(saleRequest);
        return "redirect:/";
    }
}
