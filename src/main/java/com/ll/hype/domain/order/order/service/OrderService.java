package com.ll.hype.domain.order.order.service;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.buy.repository.BuyRepository;
import com.ll.hype.domain.order.order.dto.OrderRequest;
import com.ll.hype.domain.order.order.dto.OrderResponse;
import com.ll.hype.domain.order.order.entity.Orders;
import com.ll.hype.domain.order.order.entity.SettlementStatus;
import com.ll.hype.domain.order.order.entity.Status;
import com.ll.hype.domain.order.order.repository.OrderRepository;
import com.ll.hype.domain.order.sale.entity.Sale;
import com.ll.hype.domain.order.sale.repository.SaleRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BuyRepository buyRepository;
    private final SaleRepository saleRepository;

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
    // 금액, 모델명, 사이즈가 다르면 거래불가
    // !! buyResponse 주문 저장되면 status 변경
    // 운송장번호에 택배사 .. 택배사 통일?

    public OrderResponse createOrder(OrderRequest orderRequest, long saleId, long buyId, Member member) {
        Buy buy = buyRepository.findById(buyId) //orderRequest.getBuy().getId()
                .orElseThrow(() -> new IllegalArgumentException("조회된 구매 입찰이 없습니다."));

        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new IllegalArgumentException("조회된 판매 입찰이 없습니다."));

        Orders orders = Orders.builder()
                .buy(buy)
                .sale(sale)
                .orderDate(LocalDate.now())
                .orderPrice(buy.getPrice())
                .deliveryNumber(1234567890)
                .name(buy.getMember().getName())
                .address(buy.getAddress())
                .phoneNumber(buy.getMember().getPhoneNumber())
                .status(Status.ORDER_COMPLETE)
                .settlementStatus(SettlementStatus.WAIT_PAYMENT)
                .build();
        orderRepository.save(orders);

        return OrderResponse.of(orders);
    }
}
