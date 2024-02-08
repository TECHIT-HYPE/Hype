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
public class BuyModifyPriceResponse {
    private long id;
    private Long price;
    private Long buyNowPrice;

    public static BuyModifyPriceResponse of(Buy buy, Long buyNowPrice) {
        return BuyModifyPriceResponse.builder()
                .id(buy.getId())
                .price(buy.getPrice())
                .buyNowPrice(buyNowPrice)
                .build();
    }
}
