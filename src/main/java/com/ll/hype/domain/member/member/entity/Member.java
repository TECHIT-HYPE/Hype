package com.ll.hype.domain.member.member.entity;

import com.ll.hype.global.enums.Gender;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name; // 본명

    @Column(unique = true)
    private String nickname; // 별명

    @Column(unique = true)
    private String phoneNumber; // 010-1234-5678

    @NotNull
    private LocalDate birthday;

    private int shoesSize; // 회원 신발 사이즈

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Enumerated(value = EnumType.STRING)
    private MemberRole role;

    public void updateStatus(MemberRole role) {
        this.role = role;
    }
}
