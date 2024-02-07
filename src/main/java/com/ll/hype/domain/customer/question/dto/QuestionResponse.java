package com.ll.hype.domain.customer.question.dto;

import com.ll.hype.domain.customer.answer.entity.Answer;
import com.ll.hype.domain.customer.question.entity.Question;
import com.ll.hype.domain.customer.question.entity.QuestionCategory;
import com.ll.hype.domain.member.member.entity.Member;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private Member member;
    private String questionTitle;
    private String questionContent;
    private QuestionCategory questionCategory;
    private List<Answer> answers;
    private List<String> fullPath;

    public static QuestionResponse of(Question customerQ) {
        return QuestionResponse.builder()
                .id(customerQ.getId())
                .createDate(customerQ.getCreateDate())
                .modifyDate(customerQ.getModifyDate())
                .member(customerQ.getMember())
                .questionTitle(customerQ.getQuestionTitle())
                .questionContent(customerQ.getQuestionContent())
                .questionCategory(customerQ.getQuestionCategory())
                .answers(customerQ.getAnswers())
                .build();
    }

    public static QuestionResponse of(Question customerQ, List<String> fullPath) {
        return QuestionResponse.builder()
                .id(customerQ.getId())
                .createDate(customerQ.getCreateDate())
                .modifyDate(customerQ.getModifyDate())
                .member(customerQ.getMember())
                .questionTitle(customerQ.getQuestionTitle())
                .questionContent(customerQ.getQuestionContent())
                .questionCategory(customerQ.getQuestionCategory())
                .answers(customerQ.getAnswers())
                .fullPath(fullPath)
                .build();
    }
}
