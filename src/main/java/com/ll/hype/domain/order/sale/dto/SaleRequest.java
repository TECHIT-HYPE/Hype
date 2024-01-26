package com.ll.hype.domain.order.sale.dto;

import com.ll.hype.domain.adress.adress.entity.Address;
import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.sale.entity.Sale;
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
public class SaleRequest {
    private Shoes shoes;
    private ShoesSize shoesSize;
    private Member member;
    private Long price;
    private LocalDate startDate;
    private LocalDate endDate;
    private Address address;
    private Status status;
    private String account;

    public static Sale toEntity(SaleRequest salesReqRequest) {
        return Sale.builder()
                .shoes(salesReqRequest.getShoes())
                .shoesSize(salesReqRequest.getShoesSize())
                .member(salesReqRequest.getMember())
                .price(salesReqRequest.getPrice())
                .startDate(salesReqRequest.getStartDate())
                .endDate(salesReqRequest.getEndDate())
                .address(salesReqRequest.getAddress())
                .status(salesReqRequest.getStatus())
                .account(salesReqRequest.getAccount())
                .build();
    }
}
