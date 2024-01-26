package com.ll.hype.domain.customer.question.service;

import com.ll.hype.domain.customer.question.dto.CustomerQRequest;
import com.ll.hype.domain.customer.question.dto.CustomerQResponse;
import com.ll.hype.domain.customer.question.entity.CustomerQ;
import com.ll.hype.domain.customer.question.repository.CsQRepository;
import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.repository.MemberRepository;
import com.ll.hype.global.exception.custom.UserMismatchException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CsQService {
    private final CsQRepository csQRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CustomerQ questionSave(CustomerQRequest customerQRequest, String userEmail) {
        CustomerQ customerQ = CustomerQRequest.toEntity(customerQRequest);

        Member findMember = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("이메일 조회가 불가합니다."));

        customerQ.addMember(findMember);
        csQRepository.save(customerQ);
        return customerQ;
    }

    @Transactional
    public void questionUpdate(Long id, CustomerQRequest customerQRequest, String userEmail) {
        CustomerQ findQuestion = csQRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 문의사항이 없습니다."));

        CustomerQ customerQ = CustomerQRequest.toEntity(customerQRequest);
        findQuestion.update(customerQ);
    }

    @Transactional
    public void questionDelete(Long id, String userEmail) {
        CustomerQ findQuestion = csQRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 문의사항이 없습니다."));

        Member findMember = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("이메일 조회가 불가합니다."));

        if (!findQuestion.getMember().getId().equals(findMember.getId())) {
            throw new UserMismatchException("사용자가 일치하지 않습니다.");
        }

        csQRepository.delete(findQuestion);
    }

    public CustomerQResponse findOne(Long id, String userEmail) {
        CustomerQ findQuestion = csQRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 문의사항이 없습니다."));

        Member findMember = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("조회된 사용자가 없습니다."));

        if (!findQuestion.getMember().getId().equals(findMember.getId())) {
            throw new UserMismatchException("사용자가 일치하지 않습니다.");
        }

        return CustomerQResponse.of(findQuestion);
    }

    public List<CustomerQResponse> findByMyList(String userEmail) {
        List<CustomerQResponse> questions = new ArrayList<>();
        Member findMember = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("조회된 사용자가 없습니다."));

        List<CustomerQ> findByQuestions = csQRepository.findByMember(findMember);

        for (CustomerQ csq : findByQuestions) {
            questions.add(CustomerQResponse.of(csq));
        }

        return questions;
    }
}
