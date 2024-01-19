package com.ll.hype.Hype.domain.social.socialcomment.entity;

import com.ll.hype.Hype.domain.member.member.entity.Member;
import com.ll.hype.Hype.domain.social.social.entity.Social;
import com.ll.hype.Hype.domain.social.socialrecomment.entity.SocialReComment;
import com.ll.hype.Hype.global.jpa.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialComment extends BaseEntity {
    @ManyToOne
    private Social social;

    @ManyToOne
    private Member member;

    private String comment;
    private int like;

    @OneToMany(mappedBy = "socialComment", cascade = CascadeType.ALL)
    private List<SocialReComment> socialReComments = new ArrayList<>();
}
