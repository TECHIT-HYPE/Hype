package com.ll.hype.domain.order.buy.dto;

import com.ll.hype.domain.adress.adress.entity.Address;
import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.global.enums.Status;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyRequest {
    private Shoes shoes;
    private ShoesSize shoesSize;
    private Member member;
    private Long price;
    private LocalDate startDate;
    private LocalDate endDate;
    private Address address;
    private Status status;

    public static Buy toEntity(BuyRequest orderReqRequest) {
        return Buy.builder()
                .shoes(orderReqRequest.getShoes())
                .shoesSize(orderReqRequest.getShoesSize())
                .member(orderReqRequest.getMember())
                .price(orderReqRequest.getPrice())
                .startDate(orderReqRequest.getStartDate())
                .endDate(orderReqRequest.getEndDate())
                .address(orderReqRequest.getAddress())
                .status(orderReqRequest.getStatus())
                .build();
    }
}
