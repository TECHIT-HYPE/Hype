package com.ll.hype.domain.order.order.entity;

import com.ll.hype.domain.member.role.MemberRole;
import com.ll.hype.domain.orderrequest.order.entity.OrderRequest;
import com.ll.hype.domain.salesrequest.sales.entity.SalesRequest;
import com.ll.hype.global.enums.Gender;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)

public class Order extends BaseEntity {
    @ManyToOne
    private OrderRequest orderRequest;
    @ManyToOne
    private SalesRequest salesRequest;

    private LocalDate orderDate;
    private int orderPrice;
    private int deliveryNumber;
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Enumerated(value = EnumType.STRING)
    private SettlementStatus settlementStatus;
}
