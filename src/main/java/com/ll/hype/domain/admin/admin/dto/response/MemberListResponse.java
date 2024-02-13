package com.ll.hype.domain.admin.admin.dto.response;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.entity.MemberRole;
import com.ll.hype.global.enums.Gender;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberListResponse {
    private Long id;
    private String email;
    private String name;
    private String nickname;
    private Long phoneNumber;
    private Gender gender;
    private MemberRole role;
    private Integer shoesSize;

    @Builder.Default
    private List<String> fullPath = new ArrayList<>();

    public static MemberListResponse of(Member member, List<String> fullPath) {
        return MemberListResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .gender(member.getGender())
                .role(member.getRole())
                .shoesSize(member.getShoesSize())
                .fullPath(fullPath)
                .build();
    }
}
