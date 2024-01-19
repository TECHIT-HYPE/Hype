package com.ll.hype.Hype.domain.shoes.review.entity;

import com.ll.hype.Hype.domain.member.member.entity.Member;
import com.ll.hype.Hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.Hype.global.jpa.BaseEntity;
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
public class Review extends BaseEntity {
    @ManyToOne
    private Shoes shoes;

    @ManyToOne
    private Member member;

    private String content;
}
