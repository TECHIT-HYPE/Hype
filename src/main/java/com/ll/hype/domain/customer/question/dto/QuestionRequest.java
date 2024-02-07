package com.ll.hype.domain.customer.question.dto;

import com.ll.hype.domain.customer.question.entity.Question;
import com.ll.hype.domain.customer.question.entity.QuestionCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {
    private String questionTitle;
    private String questionContent;
    private QuestionCategory questionCategory;

    public static Question toEntity(QuestionRequest CustomerQRequest) {
        return Question.builder()
                .questionTitle(CustomerQRequest.getQuestionTitle())
                .questionContent(CustomerQRequest.getQuestionContent())
                .questionCategory(CustomerQRequest.getQuestionCategory())
                .build();
    }
}
