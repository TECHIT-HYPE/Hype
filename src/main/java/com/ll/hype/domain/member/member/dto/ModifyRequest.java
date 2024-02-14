package com.ll.hype.domain.member.member.dto;

import com.ll.hype.global.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class ModifyRequest {
    private String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 항목입니다.")
    private String passwordConfirm;

    private String name; // 본명

    @NotBlank(message = "별명은 필수 항목입니다.")
    private String nickname; // 별명

    @NotNull
    private Long phoneNumber; // 010-1234-5678

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private Gender gender;

    private Integer shoesSize; // 회원 신발 사이즈

    private boolean removePhoto; // 프로필 사진 제거
}
