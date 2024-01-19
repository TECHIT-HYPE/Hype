package com.ll.hype.domain.social.social.entity;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.global.jpa.BaseEntity;
import com.ll.hype.domain.social.socialcomment.entity.SocialComment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class Social extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    private String content;
    private int likes;

    @OneToMany(mappedBy = "social", cascade = CascadeType.ALL)
    private List<SocialShoes> socialShoes = new ArrayList<>();

    @OneToMany(mappedBy = "social", cascade = CascadeType.ALL)
    private List<SocialComment> socialCommentsList = new ArrayList<>();
}
