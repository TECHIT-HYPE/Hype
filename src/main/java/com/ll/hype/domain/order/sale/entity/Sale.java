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
import org.hibernate.annotations.Comment;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Sale extends BaseEntity {
    @Comment("신발 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    private Shoes shoes;

    @Comment("신발 사이즈 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    private ShoesSize shoesSize;

    @Comment("판매 입찰 회원 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Comment("입찰가")
    private Long price;

    @Comment("입찰 시작일")
    private LocalDate startDate;

    @Comment("입찰 종료일")
    private LocalDate endDate;

    @Comment("반송 주소")
    private String address; // 반송주소

    @Comment("입찰 상태")
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Comment("환불 계좌")
    private String account; // ex) 신한은행 1002-123455-5555--88, 판매요청에만 컬럼 존재

    public void updateStatus(Status status) {
        this.status = status;
    }
}