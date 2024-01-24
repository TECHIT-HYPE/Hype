package com.ll.hype.domain.customer.customer.dto;

import com.ll.hype.domain.brand.brand.dto.BrandResponse;
import com.ll.hype.domain.customer.customer.entity.CustomerQ;
import com.ll.hype.domain.customer.customer.entity.QuestionCategory;
import com.ll.hype.domain.member.member.entity.Member;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerQResponse {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private Member member;
    private String questionTitle;
    private String questionContent;
    private QuestionCategory questionCategory;

    public static CustomerQResponse of(CustomerQ customerQ) {
        return CustomerQResponse.builder()
                .id(customerQ.getId())
                .createDate(customerQ.getCreateDate())
                .modifyDate(customerQ.getModifyDate())
                .member(customerQ.getMember())
                .questionTitle(customerQ.getQuestionTitle())
                .questionContent(customerQ.getQuestionContent())
                .questionCategory(customerQ.getQuestionCategory())
                .build();
    }
}
