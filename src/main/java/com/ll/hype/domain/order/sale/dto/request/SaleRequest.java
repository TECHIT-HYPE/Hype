package com.ll.hype.domain.order.sale.dto.request;

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
    private String address;
    private Status status;
    private String account;

    public static Sale toEntity(SaleRequest saleRequest) {
        return Sale.builder()
                .shoes(saleRequest.getShoes())
                .shoesSize(saleRequest.getShoesSize())
                .member(saleRequest.getMember())
                .price(saleRequest.getPrice())
                .startDate(saleRequest.getStartDate())
                .endDate(saleRequest.getEndDate())
                .address(saleRequest.getAddress())
                .status(saleRequest.getStatus())
                .account(saleRequest.getAccount())
                .build();
    }
}
