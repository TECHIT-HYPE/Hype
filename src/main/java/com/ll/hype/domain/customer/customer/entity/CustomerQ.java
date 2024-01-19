package com.ll.hype.domain.customer.customer.entity;

import com.ll.hype.global.enums.QuestionCategory;
import com.ll.hype.global.jpa.BaseEntity;
import com.ll.hype.domain.member.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class CustomerQ extends BaseEntity {
    @Enumerated(value = EnumType.STRING)
    private QuestionCategory questionCategory;

    @ManyToOne
    private Member member;

    private String questionTitle;
    private String questionContent;
}
