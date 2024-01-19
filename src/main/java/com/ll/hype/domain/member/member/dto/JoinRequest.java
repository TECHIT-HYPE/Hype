package com.ll.hype.domain.member.member.dto;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.global.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest {
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name; // 본명

    @NotNull
    private String nickname; // 별명

    @NotNull
    private String phoneNumber; // 010-1234-5678

    @NotNull
    private LocalDate birthday;

    private int shoesSize; // 회원 신발 사이즈

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    public static Member toEntity(JoinRequest joinRequest) {
        return Member.builder()
                .email(joinRequest.email)
                .password(joinRequest.password)
                .name(joinRequest.name)
                .nickname(joinRequest.nickname)
                .phoneNumber(joinRequest.phoneNumber)
                .birthday(joinRequest.birthday)
                .shoesSize(joinRequest.shoesSize)
                .gender(joinRequest.gender)
                .build();
    }
}
