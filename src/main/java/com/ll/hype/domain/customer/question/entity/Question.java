package com.ll.hype.domain.customer.question.entity;

import com.ll.hype.domain.customer.answer.entity.Answer;
import com.ll.hype.global.jpa.BaseEntity;
import com.ll.hype.domain.member.member.entity.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {
    @Comment("문의 카테고리")
    @Enumerated(value = EnumType.STRING)
    private QuestionCategory questionCategory;

    @Comment("회원 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Comment("문의 제목")
    private String questionTitle;

    @Comment("문의 내용")
    private String questionContent;

    @Comment("답변 목록")
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    public void update(Question customerQ) {
        this.questionTitle = customerQ.getQuestionTitle();
        this.questionContent = customerQ.getQuestionContent();
        this.questionCategory = customerQ.getQuestionCategory();
    }

    public void addMember(Member member) {
        this.member = member;
    }
}