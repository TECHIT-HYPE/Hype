package com.ll.hype.domain.order.sale.dto.response;

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
import java.util.ArrayList;
import java.util.List;

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
    private String address;
    private Status status;
    private String account;

    @Builder.Default
    private List<String> fullPath = new ArrayList<>();

    public static SaleResponse of(Sale sale, List<String> fullPath) {
        return SaleResponse.builder()
                .id(sale.getId())
                .createDate(sale.getCreateDate())
                .modifyDate(sale.getModifyDate())
                .shoes(sale.getShoes())
                .shoesSize(sale.getShoesSize())
                .member(sale.getMember())
                .price(sale.getPrice())
                .startDate(sale.getStartDate())
                .endDate(sale.getEndDate())
                .address(sale.getAddress())
                .status(sale.getStatus())
                .account(sale.getAccount())
                .fullPath(fullPath)
                .build();
    }
}
