package com.ll.hype.domain.member.member.entity;

import com.ll.hype.domain.member.role.MemberRole;
import com.ll.hype.global.enums.Gender;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@AllArgsConstructor(access =  AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phoneNumber;
    private LocalDate birthday;
    private Gender gender;
    // private Adderss address; // 우체국 API 조사 후 주소 추가해야함
    private int shoesSize;
    private MemberRole role;
}
