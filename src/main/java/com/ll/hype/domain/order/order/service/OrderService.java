package com.ll.hype.domain.order.order.service;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.buy.repository.BuyRepository;
import com.ll.hype.domain.order.order.dto.OrderRequest;
import com.ll.hype.domain.order.order.dto.OrderResponse;
import com.ll.hype.domain.order.order.entity.OrderStatus;
import com.ll.hype.domain.order.order.entity.Orders;
import com.ll.hype.domain.order.order.entity.PaymentStatus;
import com.ll.hype.domain.order.order.repository.OrderRepository;
import com.ll.hype.domain.order.sale.entity.Sale;
import com.ll.hype.domain.order.sale.repository.SaleRepository;
import java.time.LocalDate;
import java.util.List;

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
    private final BuyRepository buyRepository;
    private final SaleRepository saleRepository;
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

    // TODO
    // + 금액, 모델명, 사이즈가 다르면 거래불가
    // + !! buyResponse 주문 저장되면 status 변경
    // + 운송장번호에 택배사 .. 택배사 통일?

    public OrderResponse createOrder(OrderRequest orderRequest, long saleId, long buyId, Member member) {
        Buy buy = buyRepository.findById(buyId) //orderRequest.getBuy().getId()
                .orElseThrow(() -> new IllegalArgumentException("조회된 구매 입찰이 없습니다."));

        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new IllegalArgumentException("조회된 판매 입찰이 없습니다."));

        Orders order = Orders.builder()
                .buy(buy)
                .sale(sale)
                .orderDate(LocalDate.now())
                .orderPrice(buy.getPrice())
                .receiverName(buy.getReceiverName())
                .receiverPhoneNumber(buy.getReceiverPhoneNumber())
                .receiverAddress(buy.getReceiverAddress())
                .status(OrderStatus.TRADING)
                .paymentStatus(PaymentStatus.WAIT_PAYMENT)
                .build();
        orderRepository.save(order);

        return OrderResponse.of(order);
    }

    public void checkAmount(String tossId, String amountStr) {
        Orders order = orderRepository.findByTossId(tossId)
                .orElseThrow(() -> new IllegalArgumentException("찾는 주문이 없습니다."));

        long amount = Long.parseLong(amountStr);

        log.info("[OrderService.checkAmount] amount : " + amount);
        log.info("[OrderService.checkAmount] Order amount : " + order.getOrderPrice());

        if (amount != order.getOrderPrice()) {
            throw new IllegalArgumentException("주문금액과 결제금액이 일치하지 않습니다.");
        }
    }

    @Transactional
    public void setPaymentComplete(String tossId) {
        Orders order = orderRepository.findByTossId(tossId)
                .orElseThrow(() -> new IllegalArgumentException("찾는 주문이 없습니다."));
        log.info("[OrderService.setPaymentComplete] 여기까지는 오나?");
        order.updatePaymentStatus(PaymentStatus.COMPLETE_PAYMENT);
    }
}
