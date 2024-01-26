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
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesReqRequest {
    private Shoes shoes;
    private ShoesSize shoesSize;
    private Member member;
    private Long price;
    private LocalDate startDate;
    private LocalDate endDate;
    private Address address;
    private Status status;
    private String account;

    public static SalesRequest toEntity(SalesReqRequest salesReqRequest) {
        return SalesRequest.builder()
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
