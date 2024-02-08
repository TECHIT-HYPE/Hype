package com.ll.hype.domain.customer.question.service;

import com.ll.hype.domain.customer.question.dto.QuestionRequest;
import com.ll.hype.domain.customer.question.dto.QuestionResponse;
import com.ll.hype.domain.customer.question.entity.Question;
import com.ll.hype.domain.customer.question.repository.QuestionRepository;
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
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository csQRepository;
    private final MemberRepository memberRepository;
    private final ImageBridgeComponent imageBridgeComponent;

    @Transactional
    public QuestionResponse questionSave(QuestionRequest customerQRequest, Member member, List<MultipartFile> files) {
        Question customerQ = QuestionRequest.toEntity(customerQRequest);

        customerQ.addMember(member);
        csQRepository.save(customerQ);

        if (!files.get(0).isEmpty()) {
            imageBridgeComponent.save(ImageType.QUESTION, customerQ.getId(), files);
        }

        return QuestionResponse.of(customerQ);
    }

    // 문의사항 수정
    @Transactional
    public void questionUpdate(Long id, QuestionRequest customerQRequest, Member member) {
        Question findQuestion = csQRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 문의사항이 없습니다."));

        if (!findQuestion.getMember().getEmail().equals(member.getEmail())) {
            throw new UserMismatchException("사용자가 일치하지 않습니다.");
        }

        if (!findQuestion.getAnswers().isEmpty()) {
            throw new IllegalArgumentException("답변이 달린 문의사항은 수정이 불가능합니다.");
        }

        Question customerQ = QuestionRequest.toEntity(customerQRequest);
        findQuestion.update(customerQ);
    }

    // 문의사항 삭제
    @Transactional
    public void questionDelete(Long id, Member member) {
        Question findQuestion = csQRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 문의사항이 없습니다."));


        if (!findQuestion.getMember().getEmail().equals(member.getEmail())) {
            throw new UserMismatchException("사용자가 일치하지 않습니다.");
        }

        List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.QUESTION, findQuestion.getId());

        // 조회된 이미지가 없는 경우 이미지 삭제 작동 x
        if (!fullPath.isEmpty()) {
            imageBridgeComponent.delete(ImageType.QUESTION, findQuestion.getId());
        }

        csQRepository.delete(findQuestion);
    }

    // 문의사항 상세조회
    public QuestionResponse findOne(Long id, Member member) {
        Question question = csQRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 문의사항이 없습니다."));

        if (!question.getMember().getEmail().equals(member.getEmail())) {
            throw new UserMismatchException("사용자 정보가 일치하지 않습니다.");
        }

        List<String> fullPath = imageBridgeComponent.findAllFullPath(ImageType.QUESTION, question.getId());
        return QuestionResponse.of(question, fullPath);
    }

    // 나의 문의사항 확인
    public List<QuestionResponse> findByMyList(Member member) {
        List<QuestionResponse> questions = new ArrayList<>();
        List<Question> findByQuestions = csQRepository.findByMember(member);

        for (Question csq : findByQuestions) {
            questions.add(QuestionResponse.of(csq));
        }

        return questions;
    }
}
