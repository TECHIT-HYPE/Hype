package com.ll.hype.domain.social.profile.dto;

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
}
