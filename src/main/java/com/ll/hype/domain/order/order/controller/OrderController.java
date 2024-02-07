package com.ll.hype.domain.order.order.controller;


import com.ll.hype.domain.order.buy.dto.response.BuyResponse;
import com.ll.hype.domain.order.buy.service.BuyService;
import com.ll.hype.domain.order.order.dto.OrderRequest;
import com.ll.hype.domain.order.order.dto.OrderResponse;
import com.ll.hype.domain.order.order.service.OrderService;
import com.ll.hype.domain.order.sale.service.SaleService;
import com.ll.hype.global.security.authentication.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@RequestMapping("/order")
@Controller
public class OrderController {

    private final OrderService orderService;
    private final BuyService buyService;
    private final SaleService saleService;


    // 즉시 판매 -> 오더생성
    @PostMapping("/sale/create")
    public String createOrder(OrderRequest orderRequest,
                              @RequestParam("saleId") long saleId,
                              @RequestParam("buyId") long buyId,
                              @AuthenticationPrincipal UserPrincipal user,
                              Model model) {
        BuyResponse buyResponse = buyService.findByBuyId(buyId);
        model.addAttribute("buyData", buyResponse);

        OrderResponse orderResponse = orderService.createOrder(orderRequest, saleId, buyId, user.getMember());
        model.addAttribute("orderResponse", orderResponse);
        return "domain/order/orderDetail";
    }
}
