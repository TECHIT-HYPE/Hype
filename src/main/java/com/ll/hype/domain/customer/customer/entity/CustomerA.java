package com.ll.hype.domain.customer.customer.entity;

import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
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
public class CustomerA extends BaseEntity {
    private Long questionId;
    private Long answerMemberId;
    private String answerContent;
}
