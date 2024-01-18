package com.ll.hype.domain.salesrequest.sales.entity;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesRequest extends BaseEntity {
    @ManyToOne
    private Shoes shoesId;

    @ManyToOne
    private ShoesSize shoesSize;

    @ManyToOne
    private Member memberId;

    private Long price;
    private LocalDate startDate;
    private LocalDate endDate;
    private String account;
    private String sendAddressId;

    @Enumerated(value = EnumType.STRING)
    private Status status;
}
