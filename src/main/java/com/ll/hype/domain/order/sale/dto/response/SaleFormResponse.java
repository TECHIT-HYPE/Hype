package com.ll.hype.domain.order.sale.dto.response;

import com.ll.hype.domain.order.buy.entity.Buy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleFormResponse {
    private Buy buy;

    @Builder.Default
    private List<String> fullPath = new ArrayList<>();

    public static SaleFormResponse of(Buy buy, List<String> fullPath) {
        return SaleFormResponse.builder()
                .buy(buy)
                .fullPath(fullPath)
                .build();
    }
}
