package com.ll.hype.domain.salesrequest.sales.entity;

import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesRequest extends BaseEntity {
    private String shoesId;
    private int shoesSize;
    private Long memberId;
    private Long price;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String account;
    private String sendAddressId;
    private String status;
}
