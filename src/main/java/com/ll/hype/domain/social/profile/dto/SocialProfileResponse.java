package com.ll.hype.domain.social.profile.dto;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.social.social.entity.Social;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialProfileResponse {
    private Long id;
    private String nickname;
    private String email;
    private String profileImage;
    private List<Social> socials;
    private Long feedCount;
    private boolean followState;
    private Long followCount;

    public void updateCounts(Long followCount, Long feedCount, boolean followState) {
        this.followCount = followCount;
        this.feedCount = feedCount;
        this.followState = followState;
    }
}
