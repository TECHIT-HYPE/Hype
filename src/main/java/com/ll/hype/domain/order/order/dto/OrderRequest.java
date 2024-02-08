package com.ll.hype.domain.order.order.dto;

import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.order.entity.OrderStatus;
import com.ll.hype.domain.order.order.entity.Orders;
import com.ll.hype.domain.order.order.entity.PaymentStatus;
import com.ll.hype.domain.order.sale.entity.Sale;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String tossId;
    private Buy buy;
    private Sale sale;
    private LocalDate orderDate;
    private Long orderPrice;
    // private Long deliveryNumber;
    private String receiverName;
    private Long receiverPhoneNumber;
    private String receiverAddress;
    private OrderStatus status;
    private PaymentStatus paymentStatus;

    public static Orders toEntity(OrderRequest orderRequest) {
        return Orders.builder()
                .tossId(orderRequest.getTossId())
                .buy(orderRequest.getBuy())
                .sale(orderRequest.getSale())
                .orderDate(orderRequest.getOrderDate())
                .orderPrice(orderRequest.getOrderPrice())
//                .deliveryNumber(orderRequest.getDeliveryNumber())
                .receiverName(orderRequest.getReceiverName())
                .receiverPhoneNumber(orderRequest.getReceiverPhoneNumber())
                .receiverAddress(orderRequest.getReceiverAddress())
                .status(orderRequest.getStatus())
                .paymentStatus(orderRequest.getPaymentStatus())
                .build();
    }
}
