package com.ll.hype.domain.order.order.dto;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.order.entity.Orders;
import com.ll.hype.domain.order.order.entity.SettlementStatus;
import com.ll.hype.domain.order.order.entity.Status;
import com.ll.hype.domain.order.sale.entity.Sale;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

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
    private Status status;
    private SettlementStatus settlementStatus;


    public static Orders toEntity(OrderRequest orderRequest) {
        return Orders.builder()
                .buy(orderRequest.getBuy())
                .sale(orderRequest.getSale())
                .orderDate(orderRequest.getOrderDate())
                .orderPrice(orderRequest.getOrderPrice())
                .deliveryNumber(orderRequest.getDeliveryNumber())
                .name(orderRequest.getName())
                .address(orderRequest.getAddress())
                .phoneNumber(orderRequest.getPhoneNumber())
                .status(orderRequest.getStatus())
                .settlementStatus(orderRequest.getSettlementStatus())
                .build();
    }
}
