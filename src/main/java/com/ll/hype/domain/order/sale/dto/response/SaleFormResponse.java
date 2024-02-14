package com.ll.hype.domain.order.sale.dto.response;

import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleFormResponse {
    private Shoes shoes;
    int size;
    long price;

    @Builder.Default
    private List<String> fullPath = new ArrayList<>();

    public static SaleFormResponse of(Shoes shoes, int size, long price, List<String> fullPath) {
        return SaleFormResponse.builder()
                .shoes(shoes)
                .price(price)
                .size(size)
                .fullPath(fullPath)
                .build();
    }
}
