package com.ll.hype.domain.order.transactionrequest.entity;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.global.enums.Address;
import com.ll.hype.global.enums.Status;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
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
public class TransactionRequest extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    private OrderType orderType; // 판매요청, 구매요청

    @ManyToOne
    private Shoes shoes;

    @ManyToOne
    private ShoesSize shoesSize;

    @ManyToOne
    private Member member;

    private Long price;
    private LocalDate startDate;
    private LocalDate endDate;
    private Address address;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    private String account; // 판매요청에서만 값이 존재
}