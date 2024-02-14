package com.ll.hype.domain.order.buy.dto.response;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.global.enums.Status;
import java.util.ArrayList;
import java.util.List;
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
public class BuyResponse {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private Shoes shoes;
    private ShoesSize shoesSize;
    private Member member;
    private Long price;
    private LocalDate startDate;
    private LocalDate endDate;
    private String receiverName;
    private Long receiverPhone;
    private String receiverAddress;
    private Status status;

    @Builder.Default
    private List<String> fullPath = new ArrayList<>();

    public static BuyResponse of(Buy buy, List<String> fullPath) {
        return BuyResponse.builder()
                .id(buy.getId())
                .createDate(buy.getCreateDate())
                .modifyDate(buy.getModifyDate())
                .shoes(buy.getShoes())
                .shoesSize(buy.getShoesSize())
                .member(buy.getMember())
                .price(buy.getPrice())
                .startDate(buy.getStartDate())
                .endDate(buy.getEndDate())
                .receiverName(buy.getReceiverName())
                .receiverPhone(buy.getReceiverPhoneNumber())
                .receiverAddress(buy.getReceiverAddress())
                .status(buy.getStatus())
                .fullPath(fullPath)
                .build();
    }
}
