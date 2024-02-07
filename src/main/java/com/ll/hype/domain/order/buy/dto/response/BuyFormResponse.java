package com.ll.hype.domain.order.buy.dto.response;

import com.ll.hype.domain.order.sale.entity.Sale;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyFormResponse {
    private Shoes shoes;
    int size;
    long price;
    @Builder.Default
    private List<String> fullPath = new ArrayList<>();

    public static BuyFormResponse of(Shoes shoes, int size, long price, List<String> fullPath) {
        return BuyFormResponse.builder()
                .shoes(shoes)
                .price(price)
                .size(size)
                .fullPath(fullPath)
                .build();
    }
}
