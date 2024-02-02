package com.ll.hype.domain.order.sale.entity;

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
public class Sale extends BaseEntity {
//판매 입찰 요청
    @ManyToOne(fetch = FetchType.LAZY)
    private Shoes shoes; //신발 정보

    @ManyToOne(fetch = FetchType.LAZY)
    private ShoesSize shoesSize; //신발 사이즈 정보

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; //판매 입찰 등록인

    private Long price; //입찰가
    private LocalDate startDate;
    private LocalDate endDate;

    private String address; // 반송주소

    @Enumerated(value = EnumType.STRING)
    private Status status;

    private String account; // ex) 신한은행 1002-123455-5555--88, 판매요청에만 컬럼 존재
}