package com.ll.hype.domain.orderrequest.order.entity;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.orderrequest.order.entity.Status;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import java.time.LocalDate;
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
    private Shoes shoesId;
    private ShoesSize shoesSize;
    private Member memberId;
    private Long price;
    private LocalDate startDate;
    private LocalDate endDate;
    private Member receiveAddressId;
    private Status status;
}