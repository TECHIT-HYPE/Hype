package com.ll.hype.domain.social.socialcomment.dto;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.social.social.entity.Social;
import com.ll.hype.domain.social.social.repository.SocialRepository;
import com.ll.hype.domain.social.socialcomment.entity.SocialComment;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialCommentRequest {

    private Long id;

    @NotBlank(message =  "댓글을 입력해주세요")
    private String content;

    private Long socialId;

    private Long memberId;

    private String nickname;


    public SocialComment toEntity(Social social, Member member) {

        return SocialComment.builder()
                .content(content)
                .social(social)
                .member(member)
                .build();
    }

    public void updateValue(Long commentId, String nickname, Long memberId) {
        this.id = commentId;
        this.nickname = nickname;
        this.memberId = memberId;
    }

    public SocialCommentRequest(SocialComment socialcomment) {
        this.id = socialcomment.getId();
        this.content = socialcomment.getContent();
        this.socialId = socialcomment.getSocial().getId();
        this.memberId = socialcomment.getMember().getId();
        this.nickname = socialcomment.getMember().getNickname();
    }
}
