package com.ll.hype.domain.order.buy.dto.response;

import com.ll.hype.domain.order.sale.entity.Sale;
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
    private Sale sale;

    @Builder.Default
    private List<String> fullPath = new ArrayList<>();

    public static BuyFormResponse of(Sale sale, List<String> fullPath) {
        return BuyFormResponse.builder()
                .sale(sale)
                .fullPath(fullPath)
                .build();
    }
}
