package com.ll.hype.domain.order.order.dto.response;

import com.ll.hype.domain.customer.question.dto.QuestionResponse;
import com.ll.hype.domain.customer.question.entity.Question;
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
public class OrderBuyResponse {
    private Long id;
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

    @Builder.Default
    private List<String> fullPath = new ArrayList<>();

    public static OrderBuyResponse of(Orders order, List<String>fullPath) {
        return OrderBuyResponse.builder()
                .id(order.getId())
                .tossId(order.getTossId())
                .buy(order.getBuy())
                .sale(order.getSale())
                .orderDate(order.getOrderDate())
                .orderPrice(order.getOrderPrice())
                .receiverName(order.getReceiverName())
                .receiverPhoneNumber(order.getReceiverPhoneNumber())
                .receiverAddress(order.getReceiverAddress())
                .status(order.getStatus())
                .paymentStatus(order.getPaymentStatus())
                .fullPath(fullPath)
                .build();
    }
}
