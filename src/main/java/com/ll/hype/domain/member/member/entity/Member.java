package com.ll.hype.domain.member.member.entity;

import com.ll.hype.domain.customer.customer.entity.CustomerQ;
import com.ll.hype.domain.member.role.MemberRole;
import com.ll.hype.global.enums.Gender;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    // private Adderss address; // 우체국 API 조사 후 주소 추가해야함
    private int shoesSize;

    @Enumerated(value = EnumType.STRING)
    private MemberRole role;

}
