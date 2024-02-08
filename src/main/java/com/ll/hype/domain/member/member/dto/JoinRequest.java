package com.ll.hype.domain.member.member.dto;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.global.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest {
    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Email(message="올바른 이메일 형식을 입력해주세요.")
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
    @Pattern(regexp="^[0-9]+$", message="올바른 전화번호 형식을 입력해주세요.")
    private Long phoneNumber;

    @NotNull(message = "생년월일는 필수 항목입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotNull(message = "성별 필수 항목입니다.")
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
