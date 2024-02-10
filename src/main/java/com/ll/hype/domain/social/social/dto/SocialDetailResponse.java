package com.ll.hype.domain.social.social.dto;

import com.ll.hype.domain.member.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class SocialDetailResponse {
    private Long socialId;
    private Member member;
    private String content;
    private String profileImage;
    private LocalDateTime createDate;
    private List<String> postImages;
    private Long likesCount;
    private boolean likesState;

    public void updateLikesInfo(Long likesCount, boolean likesState) {
        this.likesCount = likesCount;
        this.likesState = likesState;
    }
}
