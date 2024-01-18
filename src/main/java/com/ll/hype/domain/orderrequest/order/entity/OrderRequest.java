package com.ll.hype.domain.orderrequest.order.entity;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.orderrequest.order.entity.Status;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import java.time.LocalDate;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderRequest extends BaseEntity {
    @ManyToOne
    private Shoes shoesId;

    @ManyToOne
    private ShoesSize shoesSize;

    @ManyToOne
    private Member memberId;

    private Long price;
    private LocalDate startDate;
    private LocalDate endDate;
    private String receiveAddressId;

    @Enumerated(value = EnumType.STRING)
    private Status status;
}