package com.ll.hype.domain.customer.question.entity;

import com.ll.hype.domain.customer.answer.entity.CustomerA;
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

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerQ extends BaseEntity {
    @Enumerated(value = EnumType.STRING)
    private QuestionCategory questionCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String questionTitle;
    private String questionContent;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<CustomerA> answers = new ArrayList<>();

    public void update(CustomerQ customerQ) {
        this.questionTitle = customerQ.getQuestionTitle();
        this.questionContent = customerQ.getQuestionContent();
        this.questionCategory = customerQ.getQuestionCategory();
    }

    public void addMember(Member member) {
        this.member = member;
    }
}