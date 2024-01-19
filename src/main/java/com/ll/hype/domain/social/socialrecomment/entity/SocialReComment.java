package com.ll.hype.domain.social.socialrecomment.entity;

import com.ll.hype.global.jpa.BaseEntity;
import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.social.socialcomment.entity.SocialComment;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialReComment extends BaseEntity {
    @ManyToOne
    private SocialComment socialBoardComment;

    @ManyToOne
    private Member member;

    private String reComment;
    private int like;
}
