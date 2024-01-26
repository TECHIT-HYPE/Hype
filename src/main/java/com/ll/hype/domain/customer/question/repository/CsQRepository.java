package com.ll.hype.domain.customer.question.repository;

import com.ll.hype.domain.customer.question.entity.CustomerQ;
import com.ll.hype.domain.member.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CsQRepository extends JpaRepository<CustomerQ, Long> {
    List<CustomerQ> findByMember(Member member);
}
