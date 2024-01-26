package com.ll.hype.domain.order.salesrequest.dto;

import com.ll.hype.domain.adress.adress.entity.Address;
import com.ll.hype.domain.brand.brand.entity.Brand;
import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.salesrequest.entity.SalesRequest;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesCategory;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.global.enums.Gender;
import com.ll.hype.global.enums.Status;
import com.ll.hype.global.enums.StatusCode;
import jakarta.persistence.*;
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
public class SalesReqResponse {
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

    public static SalesReqResponse of(SalesRequest salesRequest) {
        return SalesReqResponse.builder()
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
