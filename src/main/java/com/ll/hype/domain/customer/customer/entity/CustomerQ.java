package com.ll.hype.domain.customer.customer.entity;

import com.ll.hype.global.enums.QuestionCategory;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
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
    private QuestionCategory questionCategory;
    private Long memberId;
    private String questionTitle;
    private String questionContent;
}
