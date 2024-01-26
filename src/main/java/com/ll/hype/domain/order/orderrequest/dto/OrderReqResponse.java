package com.ll.hype.domain.order.orderrequest.dto;

import com.ll.hype.domain.adress.adress.entity.Address;
import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.orderrequest.entity.OrderRequest;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.global.enums.Status;
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
public class OrderReqResponse {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private Shoes shoes;
    private ShoesSize shoesSize;
    private Member member;
    private Long price;
    private LocalDate startDate;
    private LocalDate endDate;
    private Address address;
    private Status status;

    public static OrderReqResponse of(OrderRequest orderRequest) {
        return OrderReqResponse.builder()
                .id(orderRequest.getId())
                .createDate(orderRequest.getCreateDate())
                .modifyDate(orderRequest.getModifyDate())
                .shoes(orderRequest.getShoes())
                .shoesSize(orderRequest.getShoesSize())
                .member(orderRequest.getMember())
                .price(orderRequest.getPrice())
                .startDate(orderRequest.getStartDate())
                .endDate(orderRequest.getEndDate())
                .address(orderRequest.getAddress())
                .status(orderRequest.getStatus())
                .build();
    }
}
