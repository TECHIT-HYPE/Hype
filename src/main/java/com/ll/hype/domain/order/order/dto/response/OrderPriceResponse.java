package com.ll.hype.domain.order.order.dto.response;

import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.order.entity.OrderStatus;
import com.ll.hype.domain.order.order.entity.Orders;
import com.ll.hype.domain.order.order.entity.PaymentStatus;
import com.ll.hype.domain.order.sale.entity.Sale;
import java.time.LocalDate;
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
public class OrderPriceResponse {
    private Long id;
    private Buy buy;
    private Sale sale;
    private long orderPrice;

    public static OrderPriceResponse of(Orders order) {
        return OrderPriceResponse.builder()
                .id(order.getId())
                .buy(order.getBuy())
                .sale(order.getSale())
                .orderPrice(order.getOrderPrice())
                .build();
    }
}
