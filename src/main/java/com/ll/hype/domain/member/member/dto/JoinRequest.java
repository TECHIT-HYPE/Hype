package com.ll.hype.domain.member.member.dto;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.global.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest {
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 항목입니다.")
    private String passwordConfirm;

    @NotBlank(message = "본명은 필수 항목입니다.")
    private String name; // 본명

    @NotBlank(message = "별명은 필수 항목입니다.")
    private String nickname; // 별명

    @NotBlank(message = "전화번호는 필수 항목입니다.")
    private String phoneNumber; // 010-1234-5678

    @NotNull(message = "생년월일는 필수 항목입니다.")
    private LocalDate birthday;

    @NotNull(message = "성별 필수 항목입니다.")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private Integer shoesSize; // 회원 신발 사이즈

    public static Member toEntity(JoinRequest joinRequest) {
        return Member.builder()
                .email(joinRequest.email)
                .password(joinRequest.password)
                .name(joinRequest.name)
                .nickname(joinRequest.nickname)
                .phoneNumber(joinRequest.phoneNumber)
                .birthday(joinRequest.birthday)
                .gender(joinRequest.gender)
                .shoesSize(joinRequest.shoesSize)
                .build();
    }
}
