package com.ll.hype.domain.customer.customer.entity;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    @OneToOne
    private CustomerQ question;

    @ManyToOne
    private Member member;

    private String answerContent;
}