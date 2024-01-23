package com.ll.hype.domain.member.mypage.dto;

import com.ll.hype.global.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyRequest {
    private String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 항목입니다.")
    private String passwordConfirm;

    private String name; // 본명

    @NotBlank(message = "별명은 필수 항목입니다.")
    private String nickname; // 별명

    @NotBlank(message = "전화번호는 필수 항목입니다.")
    @Pattern(regexp="^\\d{2,3}-\\d{3,4}-\\d{4}$", message="올바른 전화번호 형식을 입력해주세요.")
    private String phoneNumber; // 010-1234-5678

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private Gender gender;

    private Integer shoesSize; // 회원 신발 사이즈
}
