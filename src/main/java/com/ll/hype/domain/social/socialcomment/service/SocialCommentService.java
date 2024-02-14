package com.ll.hype.domain.social.socialcomment.service;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.repository.MemberRepository;
import com.ll.hype.domain.social.social.entity.Social;
import com.ll.hype.domain.social.social.repository.SocialRepository;
import com.ll.hype.domain.social.socialcomment.dto.SocialCommentRequest;
import com.ll.hype.domain.social.socialcomment.entity.SocialComment;
import com.ll.hype.domain.social.socialcomment.repository.SocialCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class SocialCommentService {
    private final SocialCommentRepository socialCommentRepository;
    private final SocialRepository socialRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public SocialComment postComment(Long socialId, SocialCommentRequest socialCommentRequest, Member member) {
        Social social = socialRepository.findById(socialId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid social id: " + socialId));
        SocialComment socialComment = SocialComment.builder()
                        .content(socialCommentRequest.getContent())
                .social(social)
                                .member(member).build();

        socialCommentRepository.save(socialComment);
        socialCommentRequest.updateValue(socialComment.getId(), member.getNickname(), member.getId());
        return socialComment;
    }

    @Transactional
    public void deleteComment(Long socialCommentId) {
        socialCommentRepository.deleteById(socialCommentId);
    }
}

