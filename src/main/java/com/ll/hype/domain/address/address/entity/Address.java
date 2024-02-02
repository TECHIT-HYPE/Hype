package com.ll.hype.domain.address.address.entity;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private String addressName;

    @NotNull
    private String postcode;

    @NotNull
    private String address;

    @NotNull
    private String detailAddress;

    private String extraAddress;

    private boolean isPrimary; // 대표주소 여부

    public String getFullAddress() {
        String fullAddress = postcode + " " + address + " " + detailAddress;

        if (extraAddress != null) {
            fullAddress += (" " + extraAddress);
        }

        return fullAddress;
    }

    public void change(String addressName, String postcode, String address, String detailAddress, String extraAddress, boolean primary) {
        this.addressName = addressName;
        this.postcode = postcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.extraAddress = extraAddress;
        this.isPrimary = primary;
    }
}
