package com.ll.hype.domain.order.sale.dto.response;

import com.ll.hype.domain.order.buy.dto.response.BuyModifyPriceResponse;
import com.ll.hype.domain.order.sale.entity.Sale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleModifyPriceResponse {
    private long id;
    private Long price;
    private Long saleNowPrice;

    public static SaleModifyPriceResponse of(Sale sale, Long saleNowPrice) {
        return SaleModifyPriceResponse.builder()
                .id(sale.getId())
                .price(sale.getPrice())
                .saleNowPrice(saleNowPrice)
                .build();
    }
}
