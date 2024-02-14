package com.ll.hype.domain.order.order.entity;

import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.sale.entity.Sale;
import com.ll.hype.global.enums.Status;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders extends BaseEntity {
    @Comment("토스페이먼츠 오더 고유 값")
    private String tossId;

    @Comment("구매정보")
    @ManyToOne(fetch = FetchType.LAZY)
    private Buy buy;

    @Comment("판매정보")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sale sale;

    @Comment("거래 성사 일자")
    private LocalDate orderDate;

    @Comment("거래 성사 금액")
    private Long orderPrice;

    @Comment("운소장 번호")
    private Long deliveryNumber;

    @Comment("받는사람 이름")
    private String receiverName;

    @Comment("받는 사람 연락처")
    private Long receiverPhoneNumber;

    @Comment("받는사람 주소")
    private String receiverAddress;

    @Comment("거래 상태")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @Comment("구매자정산상태")
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Comment("판매자정산상태")
    @Enumerated(value = EnumType.STRING)
    private DepositStatus depositStatus;

    public String createTossId() {
        return super.getId() + "-" + orderDate;
    }

    public void updateTossId(String tossId) {
        this.tossId = tossId;
    }

    public void updatePaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        this.buy.updateStatus(Status.BID_COMPLETE);
        this.sale.updateStatus(Status.BID_COMPLETE);
    }

    public void updateDeliveryNumber(Long deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }
    public void updateDepositStatus(DepositStatus depositStatus) {
        this.depositStatus = depositStatus;
    }
}
