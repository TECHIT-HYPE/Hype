package com.ll.hype.domain.order.order.dto;

import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.order.entity.Orders;
import com.ll.hype.domain.order.order.entity.SettlementStatus;
import com.ll.hype.domain.order.order.entity.Status;
import com.ll.hype.domain.order.sale.entity.Sale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private Status status;
    private SettlementStatus settlementStatus;

    public static OrderResponse of(Orders orders) {
        return OrderResponse.builder()
                .id(orders.getId())
                .createDate(orders.getCreateDate())
                .modifyDate(orders.getModifyDate())
                .buy(orders.getBuy())
                .sale(orders.getSale())
                .orderDate(orders.getOrderDate())
                .orderPrice(orders.getOrderPrice())
                .deliveryNumber(orders.getDeliveryNumber())
                .name(orders.getName())
                .address(orders.getAddress())
                .phoneNumber(orders.getPhoneNumber())
                .status(orders.getStatus())
                .settlementStatus(orders.getSettlementStatus())
                .build();
    }
}
