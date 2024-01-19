package com.ll.hype.domain.order.order.entity;

import com.ll.hype.domain.adress.adress.entity.Address;
import com.ll.hype.domain.order.orderrequest.entity.OrderRequest;
import com.ll.hype.domain.order.orderrequest.entity.SalesRequest;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderRequest orderRequest; // 구매정보

    @ManyToOne(fetch = FetchType.LAZY)
    private SalesRequest salesRequest; // 판매정보

    private LocalDate orderDate; // 거래 성사 일자
    private int orderPrice; // 거래 성사 금액
    private int deliveryNumber; // 운소장 번호

    private String name; // 받는사람 이름

    @OneToOne
    private Address address; // 받는사람 주소

    private String phoneNumber; // 받는 사람 연락처

    @Enumerated(value = EnumType.STRING)
    private Status status; // 거래 상태 (거래완료, 거래취소, 반품 진행 중, 반품완료, 거래중)

    @Enumerated(value = EnumType.STRING)
    private SettlementStatus settlementStatus; // 정산상태 (결제완료(사용자), 결제취소(사용자), 미입금(관리자), 입금(관리자))
}
