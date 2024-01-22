package com.ll.hype.domain.order.orderrequest.entity;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.global.enums.Status;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesRequest
        extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Shoes shoes;

    @ManyToOne(fetch = FetchType.LAZY)
    private ShoesSize shoesSize;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private Long price;
    private LocalDate startDate;
    private LocalDate endDate;
//    private Address address;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    private String account; // 판매요청에서만 값이 존재
}