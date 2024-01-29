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

    public static Buy toEntity(BuyRequest buyRequest) {
        return Buy.builder()
                .shoes(buyRequest.getShoes())
                .shoesSize(buyRequest.getShoesSize())
                .member(buyRequest.getMember())
                .price(buyRequest.getPrice())
                .startDate(buyRequest.getStartDate())
                .endDate(buyRequest.getEndDate())
                .address(buyRequest.getAddress())
                .status(buyRequest.getStatus())
                .build();
    }
}
