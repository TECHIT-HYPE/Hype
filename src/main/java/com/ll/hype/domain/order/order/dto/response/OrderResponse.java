package com.ll.hype.domain.order.order.dto.response;

import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.order.entity.DepositStatus;
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
public class OrderResponse {
    private Long id;
    private String tossId;
    private Buy buy;
    private Sale sale;
    private LocalDate orderDate;
    private Long orderPrice;
    private Long deliveryNumber;
    private String receiverName;
    private Long receiverPhoneNumber;
    private String receiverAddress;
    private OrderStatus status;
    private PaymentStatus paymentStatus;
    private DepositStatus depositStatus;

    @Builder.Default
    private List<String> fullPath = new ArrayList<>();

    public static OrderResponse of(Orders order, List<String>fullPath) {
        return OrderResponse.builder()
                .id(order.getId())
                .tossId(order.getTossId())
                .buy(order.getBuy())
                .sale(order.getSale())
                .orderDate(order.getOrderDate())
                .orderPrice(order.getOrderPrice())
                .deliveryNumber(order.getDeliveryNumber())
                .receiverName(order.getReceiverName())
                .receiverPhoneNumber(order.getReceiverPhoneNumber())
                .receiverAddress(order.getReceiverAddress())
                .status(order.getStatus())
                .paymentStatus(order.getPaymentStatus())
                .depositStatus(order.getDepositStatus())
                .fullPath(fullPath)
                .build();
    }
}
