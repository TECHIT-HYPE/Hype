package com.ll.hype.domain.social.socialcomment.controller;

import com.ll.hype.domain.social.social.dto.SocialDetailResponse;
import com.ll.hype.domain.social.social.service.SocialDetailService;
import com.ll.hype.domain.social.socialcomment.dto.SocialCommentRequest;
import com.ll.hype.domain.social.socialcomment.entity.SocialComment;
import com.ll.hype.domain.social.socialcomment.service.SocialCommentService;
import com.ll.hype.global.security.authentication.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;

@RequiredArgsConstructor
@Controller
@RequestMapping("/social/feed")
public class SocialCommentController {
    private final SocialCommentService socialCommentService;
    private final SocialDetailService socialDetailService;

    @PostMapping("/{id}/comment/post")
    public String postComment(@PathVariable("id") Long socialId,
                              @RequestParam("content") String content,
                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long principalId = userPrincipal.getMember().getId();
        SocialCommentRequest socialCommentRequest = SocialCommentRequest.builder()
                .content(content)
                .build();


        socialCommentService.postComment(socialId, socialCommentRequest, userPrincipal.getMember());

        // SocialDetailResponse 업데이트
        SocialDetailResponse socialDetailResponse = socialDetailService.findSocial(socialId, principalId);
        socialDetailResponse.addComment(socialCommentRequest); // 새로운 댓글 추가
        return "redirect:/social/feed/%s".formatted(socialId);
    }

    @PostMapping("/{id}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable("id") Long socialId, @PathVariable Long commentId) {
        socialCommentService.deleteComment(commentId);
        return "redirect:/social/profile/%s".formatted(socialId);
    }
}
