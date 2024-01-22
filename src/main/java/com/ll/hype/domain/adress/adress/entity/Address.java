package com.ll.hype.domain.adress.adress.entity;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class Address extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String postcode;
    private String address;
    private String detailAddress;
    private String extraAddress;

}