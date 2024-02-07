package com.ll.hype.domain.order.order.dto;

import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.order.entity.OrderStatus;
import com.ll.hype.domain.order.order.entity.Orders;
import com.ll.hype.domain.order.order.entity.PaymentStatus;
import com.ll.hype.domain.order.sale.entity.Sale;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
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
    // OrderResponse 반환객체 수정 부탁드립니다.
    public static OrderResponse of(Orders orders) {
        return OrderResponse.builder()
//                .id(orders.getId())
//                .createDate(orders.getCreateDate())
//                .modifyDate(orders.getModifyDate())
//                .buy(orders.getBuy())
//                .sale(orders.getSale())
//                .orderDate(orders.getOrderDate())
//                .orderPrice(orders.getOrderPrice())
//                .deliveryNumber(orders.getDeliveryNumber())
//                .name(orders.getName())
//                .address(orders.getAddress())
//                .phoneNumber(orders.getPhoneNumber())
//                .status(orders.getStatus())
//                .settlementStatus(orders.getSettlementStatus())
                .build();
    }
}
