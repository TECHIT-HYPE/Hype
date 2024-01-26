package com.ll.hype.domain.order.sale.dto;

import com.ll.hype.domain.adress.adress.entity.Address;
import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.sale.entity.Sale;
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
public class SaleResponse {
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
    private String account;

    public static SaleResponse of(Sale salesRequest) {
        return SaleResponse.builder()
                .id(salesRequest.getId())
                .createDate(salesRequest.getCreateDate())
                .modifyDate(salesRequest.getModifyDate())
                .shoes(salesRequest.getShoes())
                .shoesSize(salesRequest.getShoesSize())
                .member(salesRequest.getMember())
                .price(salesRequest.getPrice())
                .startDate(salesRequest.getStartDate())
                .endDate(salesRequest.getEndDate())
                .address(salesRequest.getAddress())
                .status(salesRequest.getStatus())
                .account(salesRequest.getAccount())
                .build();
    }
}
