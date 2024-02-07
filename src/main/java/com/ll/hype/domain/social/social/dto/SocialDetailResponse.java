package com.ll.hype.domain.social.social.dto;

import com.ll.hype.domain.member.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SocialDetailResponse {
    private Long socialId;
    private Member member;
    private String content;
    private String profileImage;
    private List<String> postImages;
}
