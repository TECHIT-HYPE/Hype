package com.ll.hype.domain.order.order.dto;

import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.order.entity.OrderStatus;
import com.ll.hype.domain.order.order.entity.Orders;
import com.ll.hype.domain.order.order.entity.PaymentStatus;
import com.ll.hype.domain.order.sale.entity.Sale;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Buy buy;
    private Sale sale;
    private LocalDate orderDate;
    private Long orderPrice;
    private int deliveryNumber;
    private String name;
    private String address;
    private String phoneNumber;
    private OrderStatus status;
    private PaymentStatus settlementStatus;

    // TODO
    // OrderRequest 반환객체 수정 부탁드립니다.

    public static Orders toEntity(OrderRequest orderRequest) {
        return Orders.builder()
//                .buy(orderRequest.getBuy())
//                .sale(orderRequest.getSale())
//                .orderDate(orderRequest.getOrderDate())
//                .orderPrice(orderRequest.getOrderPrice())
//                .deliveryNumber(orderRequest.getDeliveryNumber())
//                .name(orderRequest.getName())
//                .address(orderRequest.getAddress())
//                .phoneNumber(orderRequest.getPhoneNumber())
//                .status(orderRequest.getStatus())
//                .settlementStatus(orderRequest.getSettlementStatus())
                .build();
    }
}
