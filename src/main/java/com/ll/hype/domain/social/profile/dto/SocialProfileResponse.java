package com.ll.hype.domain.social.profile.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialProfileResponse {
    private Long id;
    private String nickname;
    private String email;
    private String profileImage;
}
