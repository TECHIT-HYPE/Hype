package com.ll.hype.domain.customer.question.service;

import com.ll.hype.domain.customer.question.dto.CustomerQRequest;
import com.ll.hype.domain.customer.question.dto.CustomerQResponse;
import com.ll.hype.domain.customer.question.entity.CustomerQ;
import com.ll.hype.domain.customer.question.repository.CsQRepository;
import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.repository.MemberRepository;
import com.ll.hype.global.exception.custom.UserMismatchException;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class CsQService {
    private final CsQRepository csQRepository;
    private final MemberRepository memberRepository;
    private final ImageBridgeComponent imageBridgeComponent;

    @Transactional
    public CustomerQResponse questionSave(CustomerQRequest customerQRequest, String email, List<MultipartFile> files) {
        CustomerQ customerQ = CustomerQRequest.toEntity(customerQRequest);

        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 사용자가 없습니다."));

        customerQ.addMember(findMember);
        csQRepository.save(customerQ);

        if (!files.get(0).isEmpty()) {
            imageBridgeComponent.save(ImageType.QUESTION, customerQ.getId(), files);
        }

        return CustomerQResponse.of(customerQ);
    }

    // 문의사항 수정
    @Transactional
    public void questionUpdate(Long id, CustomerQRequest customerQRequest, String email) {
        CustomerQ findQuestion = csQRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 문의사항이 없습니다."));

        if (!findQuestion.getMember().getEmail().equals(email)) {
            throw new UserMismatchException("사용자가 일치하지 않습니다.");
        }

        if (!findQuestion.getAnswers().isEmpty()) {
            throw new IllegalArgumentException("답변이 달린 문의사항은 수정이 불가능합니다.");
        }

        CustomerQ customerQ = CustomerQRequest.toEntity(customerQRequest);
        findQuestion.update(customerQ);
    }

    // 문의사항 삭제
    @Transactional
    public void questionDelete(Long id, String email) {
        CustomerQ findQuestion = csQRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 문의사항이 없습니다."));


        if (!findQuestion.getMember().getEmail().equals(email)) {
            throw new UserMismatchException("사용자가 일치하지 않습니다.");
        }

        imageBridgeComponent.delete(ImageType.QUESTION, findQuestion.getId());
        csQRepository.delete(findQuestion);
    }

    // 문의사항 상세조회
    public CustomerQResponse findOne(Long id, String userEmail) {
        CustomerQ question = csQRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 문의사항이 없습니다."));

        if (!question.getMember().getEmail().equals(userEmail)) {
            throw new UserMismatchException("사용자 정보가 일치하지 않습니다.");
        }

        List<String> fullPath = imageBridgeComponent.findAllFullPath(ImageType.QUESTION, question.getId());
        return CustomerQResponse.of(question, fullPath);
    }

    // 나의 문의사항 확인
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
