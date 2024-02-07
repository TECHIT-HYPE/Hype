package com.ll.hype.domain.order.order.entity;

import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.sale.entity.Sale;
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
    private OrderStatus status; // 거래완료, 거래취소, 반품 진행 중, 반품완료, 거래중

    @Comment("정산상태")
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus settlementStatus; // 결제대기(사용자) ,결제완료(사용자), 결제취소(사용자), 미입금(관리자), 입금(관리자)
}
