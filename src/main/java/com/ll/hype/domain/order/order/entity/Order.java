package com.ll.hype.domain.order.order.entity;

import com.ll.hype.domain.member.role.MemberRole;
import com.ll.hype.global.enums.Gender;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)

public class Order extends BaseEntity {
    private String orderRequestId;
    private String salesRequestId;
    private LocalDate orderDate;
    private int orderPrice;
    private int deliveryNumber;
    private String phoneNumber;
    private String status;
    private String settlement_status;

}
