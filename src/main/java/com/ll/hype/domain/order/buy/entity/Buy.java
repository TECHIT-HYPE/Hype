package com.ll.hype.domain.order.buy.entity;

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
public class Buy extends BaseEntity {
    @Comment("신발")
    @ManyToOne(fetch = FetchType.LAZY)
    private Shoes shoes;

    @Comment("신발 사이즈")
    @ManyToOne(fetch = FetchType.LAZY)
    private ShoesSize shoesSize;

    @Comment("구매자")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Comment("가격")
    private Long price;

    @Comment("구매 입찰 시작")
    private LocalDate startDate;

    @Comment("구매 입찰 만료 기간")
    private LocalDate endDate;

    @Comment("받는 사람 이름")
    private String receiverName;

    @Comment("받는 사람 연락처")
    private Long receiverPhoneNumber;

    @Comment("받는 사람 주소")
    private String receiverAddress;

    @Comment("거래 상태")
    @Enumerated(value = EnumType.STRING)
    private Status status; // 거래 상태 거래중 거래완료

    public void updateStatus(Status status) {
        this.status = status;
    }

    public void updatePrice(Long price) {
        this.price = price;
    }
}