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
import org.hibernate.annotations.Comment;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Address extends BaseEntity {
    @Comment("주소 보유 회원")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Comment("주소 별칭")
    @NotNull
    private String addressName;

    @Comment("우편번호")
    @NotNull
    private String postcode;

    @Comment("주소")
    @NotNull
    private String address;

    @Comment("상세 주소")
    @NotNull
    private String detailAddress;

    @Comment("동 주소")
    private String extraAddress;

    @Comment("대표 주소 여부")
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
