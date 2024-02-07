package com.ll.hype.domain.customer.question.repository;

import com.ll.hype.domain.customer.question.entity.Question;
import com.ll.hype.domain.member.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByMember(Member member);
}
