package com.ll.hype.domain.order.sale.controller;

import com.ll.hype.domain.order.buy.dto.BuyResponse;
import com.ll.hype.domain.order.buy.service.BuyService;
import com.ll.hype.domain.order.sale.dto.SaleRequest;
import com.ll.hype.domain.order.sale.dto.SaleResponse;
import com.ll.hype.domain.order.sale.service.SaleService;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.dto.ShoesSizeDTO;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.domain.shoes.shoes.service.ShoesService;
import com.ll.hype.global.util.ShoesSizeGenerator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    // TODO
    // ShoesSizeGenerator 교체
    // 신발리스트 / 상세(1~3장) / 사이즈 선택 / 약관 동의 / 입찰, 즉시거래 / 거래체결 이미지 추가
    // highestbidid가 없는 사이즈 처리
    // 판매 입찰 기한 설정
    // 47번째 optional

    //사이즈 선택
    //신발고유번호, 이름, 이미지, 사이즈 노출
    @GetMapping("/select/{id}")
    public String selectSizeForm(@PathVariable("id") long id, Model model) {
        ShoesResponse shoesResponse = shoesService.findById(id);
        model.addAttribute("shoes", shoesResponse);

        Map<Integer, Optional<BuyResponse>> highestBidBySize = buyService.getHighestBidsGroupedBySize(id);
        //사이즈가 존재하면 가격 노출, 존재하지않으면 판매 입찰
        List<ShoesSizeDTO> list = ShoesSizeGenerator.getSizes().stream()
                .map(size -> highestBidBySize.getOrDefault(size, Optional.empty())
                        .map(buyResponse -> ShoesSizeDTO.builder()
                                .size(size)
                                .price(buyResponse.getPrice())
                                .exists(true)
                                .build())
                        .orElseGet(() -> ShoesSizeDTO.builder()
                                .exists(false)
                                .size(size)
                                .build()))
                .toList();

        model.addAttribute("shoesSizeList", list);

        return "domain/salesRequest/salesRequest/selectSize";
    }

    //사이즈 선택 처리
    @PostMapping("/select/{id}")
    public String selectSize(@PathVariable("id") Long id,
                             @RequestParam("selectedSize") int selectedSize,
                             RedirectAttributes redirectAttributes) {

        Optional<BuyResponse> highestBid = buyService.getHighestBidsGroupedBySize(id).get(selectedSize);

        highestBid.ifPresent(bid -> {
            redirectAttributes.addFlashAttribute("selectedSize", selectedSize);
            redirectAttributes.addFlashAttribute("highestBidId", bid.getId());
        });

        return "redirect:/sale/approval";
    }

    //판매 약관 동의
    @GetMapping("/approval")
    public String approveForm(@ModelAttribute("selectedSize") int selectedSize,
                              @ModelAttribute("highestBidId") long highestBidId,
                              Model model) {
        System.out.println("approveForm: Selected Size: " + selectedSize);
        System.out.println("approveForm: Highest Bid ID: " + highestBidId);

        BuyResponse buyResponse = buyService.findById(highestBidId);
        model.addAttribute("buyResponse", buyResponse);

        return "domain/salesRequest/salesRequest/approve";
    }

    //판매 약관 동의 처리
    @PostMapping("/approval")
    public String approve(@RequestParam("selectedSize") int selectedSize,
                          @RequestParam("highestBidId") long highestBidId,
                          @RequestParam Map<String, String> allParams,
                          RedirectAttributes redirectAttributes) {

        boolean allTermsAgreed = allParams.entrySet().stream()
                .filter(e -> e.getKey().startsWith("term"))
                .allMatch(e -> e.getValue().equals("on"));
        System.out.println("approve: All terms agreed: " + allTermsAgreed);

        if (allTermsAgreed) {
            redirectAttributes.addFlashAttribute("selectedSize", selectedSize);
            redirectAttributes.addFlashAttribute("highestBidId", highestBidId);
            return "redirect:/sale/pricing";
        } else {
            return "redirect:/sale/approval";
        }
    }

    //신발 정보 및 예상 정산 금액 표시, 판매 입찰 생성 기능
    @GetMapping("/pricing")
    public String pricingForm(@ModelAttribute("selectedSize") int selectedSize,
                          @ModelAttribute("highestBidId") long highestBidId,
                          Model model) {
//        SaleResponse saleResponse = saleService.findById(lowestBidId);
//        model.addAttribute("saleResponse", saleResponse); // 즉시 구매가

        BuyResponse buyResponse = buyService.findById(highestBidId);
        model.addAttribute("buyResponse", buyResponse); // 즉시 판매가
        model.addAttribute("selectedSize", selectedSize); //
        model.addAttribute("highestBidId", highestBidId); //


        System.out.println("[SaleController.pricingForm] selectedSize : " + selectedSize);
        System.out.println("[SaleController.pricingForm] highestBidId : " + highestBidId);

        return "domain/salesRequest/salesRequest/pricing";
    }

    //
    @PostMapping("/pricing")
    public String pricing(@RequestParam("selectedSize") int selectedSize,
                          @RequestParam("highestBidId") long highestBidId,
                          @RequestParam("price") int price,
                          @RequestParam("saleType") String saleType,
                          RedirectAttributes redirectAttributes) {

        if ("bidSale".equals(saleType)) {
            // 즉시 판매 로직 처리
//            saleService.processBidSale(productId, price);
        } else if ("immediateSale".equals(saleType)) {
            // 입찰 판매 로직 처리
//            saleService.processImmediateSale(productId, price);
        }
        BuyResponse buyResponse = buyService.findById(highestBidId);
        redirectAttributes.addFlashAttribute("selectedSize", selectedSize);
        redirectAttributes.addAttribute("buyResponse", buyResponse);
        redirectAttributes.addFlashAttribute("price", price);
        System.out.println("[SaleController.pricing] selectedSize : " + selectedSize);
        System.out.println("[SaleController.pricing] highestBidId : " + highestBidId);

        return "redirect:/sale/{id}/{shoesSize}";
    }

    @GetMapping("/{id}/{shoesSize}")
    public String saveSaleForm(@ModelAttribute("selectedSize") int selectedSize,
                               @ModelAttribute("highestBidId") long highestBidId,
                               @ModelAttribute("price") int price,
                               @ModelAttribute("saleType") String saleType,
                               SaleRequest saleRequest, Model model) {
        model.addAttribute("selectedSize", selectedSize); //
        model.addAttribute("highestBidId", highestBidId); //
        model.addAttribute("price", price); //
        model.addAttribute("saleType", saleType); //

        return "domain/salesRequest/salesRequest/saveSale";
    }

    //판매 결정 및 정보 입력//
    @PostMapping("/select/{id}/{shoesSize}")
    public String saveSale(SaleRequest saleRequest, Model model) {
        saleService.saveSaleRequest(saleRequest);
        return "redirect:/";
    }
}
