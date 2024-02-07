package com.ll.hype.domain.member.member.entity;

import com.ll.hype.global.enums.Gender;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Comment("회원 메일")
    @NotNull
    @Column(unique = true)
    private String email;

    @Comment("회원 비밀번호")
    @NotNull
    private String password;

    @Comment("회원 본명")
    @NotNull
    private String name; // 본명

    @Comment("회원 별명")
    @NotNull
    @Column(unique = true)
    private String nickname; // 별명

    @Comment("회원 연락처")
    @NotNull
    @Column(unique = true)
    private String phoneNumber;

    @Comment("회원 생년월일")
    @NotNull
    private LocalDate birthday;

    @Comment("성별")
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Comment("회원 권한")
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private MemberRole role;

    @Comment("신발 사이즈")
    private Integer shoesSize; // 회원 신발 사이즈

    public void changeToEncodedPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void updateRole(MemberRole role) {
        this.role = role;
    }

    public void modifyProfile(String encodedPassword, String nickname, String phoneNumber, Integer shoesSize) {
        this.password = encodedPassword;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.shoesSize = shoesSize;
    }
}
