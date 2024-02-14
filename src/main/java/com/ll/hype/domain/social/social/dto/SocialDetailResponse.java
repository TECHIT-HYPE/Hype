package com.ll.hype.domain.social.social.dto;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.social.social.entity.Social;
import com.ll.hype.domain.social.socialcomment.dto.SocialCommentRequest;
import com.ll.hype.domain.social.socialcomment.entity.SocialComment;
import com.ll.hype.global.s3.image.ImageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private List<SocialCommentRequest> socialCommentRequestList;
    private List<SocialShoesRequest> socialShoesRequestList;


    public void addComment(SocialCommentRequest comment) {
        if (socialCommentRequestList == null) {
            socialCommentRequestList = new ArrayList<>();
        }
        socialCommentRequestList.add(comment);
    }
}

