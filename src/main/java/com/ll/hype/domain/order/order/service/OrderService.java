package com.ll.hype.domain.order.order.service;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.order.dto.response.OrderResponse;
import com.ll.hype.domain.order.order.entity.DepositStatus;
import com.ll.hype.domain.order.order.entity.OrderStatus;
import com.ll.hype.domain.order.order.entity.Orders;
import com.ll.hype.domain.order.order.entity.PaymentStatus;
import com.ll.hype.domain.order.order.repository.OrderRepository;
import com.ll.hype.domain.order.order.util.validate.OrderValidator;
import com.ll.hype.domain.order.sale.entity.Sale;

import java.util.ArrayList;
import java.util.List;

import com.ll.hype.global.exception.custom.EntityNotFoundException;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ImageBridgeComponent imageBridgeComponent;

    // 거래 체결
    public Object createOrder(Buy buy, Sale sale) {
        return null;
    }


    // 환불로 인한 거래 취소
    public Object refundCancelOrder(Orders orders) {
        return null;
    }

    // 미입금으로 인한 거래 취소
    public Object nonDepositCancelOrder(Orders orders) {
        return null;
    }

    public void checkAmount(String tossId, String amountStr) {
        Orders order = orderRepository.findByTossId(tossId)
                .orElseThrow(() -> new IllegalArgumentException("찾는 주문이 없습니다."));

        long amount = Long.parseLong(amountStr);

        if (amount != order.getOrderPrice()) {
            throw new IllegalArgumentException("주문금액과 결제금액이 일치하지 않습니다.");
        }
    }

    @Transactional
    public void setPaymentComplete(String tossId) {
        Orders order = orderRepository.findByTossId(tossId)
                .orElseThrow(() -> new IllegalArgumentException("찾는 주문이 없습니다."));
        order.updatePaymentStatus(PaymentStatus.COMPLETE_PAYMENT);
    }

    public List<OrderResponse> findTradingOrderBuy(Member member) {
        List<Orders> tradingOrderAll = orderRepository.findOrderBuyByMember(member);
        List<OrderResponse> orderResponses = new ArrayList<>();

        for (Orders order : tradingOrderAll) {
            List<String> fullPath = imageBridgeComponent.findOneFullPath(
                    ImageType.SHOES, order.getSale().getShoes().getId());
            OrderResponse orderResponse = OrderResponse.of(order, fullPath);
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }

    public List<OrderResponse> findTradingOrderSale(Member member) {
        List<Orders> tradingOrderAll = orderRepository.findOrderSaleByMember(member);
        List<OrderResponse> orderResponses = new ArrayList<>();

        for (Orders order : tradingOrderAll) {
            List<String> fullPath = imageBridgeComponent.findOneFullPath(
                    ImageType.SHOES, order.getSale().getShoes().getId());
            OrderResponse orderResponse = OrderResponse.of(order, fullPath);
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }

    // 운송장번호 수정(등록)
    @Transactional
    public void updateDeliveryNumber(long id, long deliveryNumber, Member member) {
        Orders order = orderRepository.findByIdAndSaleMember(id, member)
                .orElseThrow(() -> new EntityNotFoundException("조회된 거래 내역이 없습니다."));
        OrderValidator.checkUserMatch(order, member);
        order.updateDeliveryNumber(deliveryNumber);
    }

    // 판매 정산 내역
    public List<OrderResponse> settlementOrder(Member member) {
        List<Orders> saleOrder = orderRepository.findBySaleMember(member);
        List<OrderResponse> orderResponses = new ArrayList<>();

        for (Orders order : saleOrder) {
            List<String> fullPath = imageBridgeComponent.findOneFullPath(
                    ImageType.SHOES, order.getSale().getShoes().getId());
            OrderResponse orderResponse = OrderResponse.of(order, fullPath);
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }

    public OrderResponse findOrder(Long id, Member member) {
        Orders order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order Not Found"));

        if (!member.getId().equals(order.getBuy().getMember().getId())) {
            throw new IllegalArgumentException("결제 정보가 다릅니다.");
        }

        List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES,
                order.getBuy().getShoes().getId());

        return OrderResponse.of(order, fullPath);
    }

    @Transactional
    public void orderComplete(Long id, Member member) {
        Orders order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order Not Found"));
        if (!member.getId().equals(order.getBuy().getMember().getId())) {
            throw new IllegalArgumentException("결제 정보가 다릅니다.");
        }

        order.updateDepositStatus(DepositStatus.NEED_DEPOSIT);
        order.updateOrderStatus(OrderStatus.TRADE_COMPLETE);
    }

}
