package com.ll.hype.domain.order.orderrequest.entity;

import com.ll.hype.domain.adress.adress.entity.Address;
import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.global.enums.Status;
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
public class OrderRequest extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Shoes shoes; //신발

    @ManyToOne(fetch = FetchType.LAZY)
    private ShoesSize shoesSize; // 신발 사이즈

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; // 구매자

    private Long price; // 가격
    private LocalDate startDate; // 구매 입찰 시작
    private LocalDate endDate; // 구매 입찰 만료 기간

    @OneToOne
    private Address address; //주소

    @Enumerated(value = EnumType.STRING)
    private Status status; // 거래 상태 거래중 거래완료
}