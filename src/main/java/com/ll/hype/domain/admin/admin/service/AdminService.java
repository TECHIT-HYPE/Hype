package com.ll.hype.domain.admin.admin.service;

import com.ll.hype.domain.admin.admin.dto.request.MemberModifyRequest;
import com.ll.hype.domain.admin.admin.dto.response.MemberListResponse;
import com.ll.hype.domain.brand.brand.dto.BrandRequest;
import com.ll.hype.domain.brand.brand.dto.BrandResponse;
import com.ll.hype.domain.brand.brand.entity.Brand;
import com.ll.hype.domain.brand.brand.repository.BrandRepository;
import com.ll.hype.domain.customer.answer.entity.Answer;
import com.ll.hype.domain.customer.answer.repository.AnswerRepository;
import com.ll.hype.domain.customer.question.dto.QuestionResponse;
import com.ll.hype.domain.customer.question.entity.Question;
import com.ll.hype.domain.customer.question.repository.QuestionRepository;
import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.repository.MemberRepository;
import com.ll.hype.domain.order.buy.dto.response.BuyResponse;
import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.buy.repository.BuyRepository;
import com.ll.hype.domain.shoes.shoes.dto.ShoesRequest;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.domain.shoes.shoes.repository.ShoesRepository;
import com.ll.hype.domain.shoes.shoes.repository.ShoesSizeRepository;
import com.ll.hype.global.enums.StatusCode;
import com.ll.hype.global.exception.custom.EntityNotFoundException;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminService {
    private final BrandRepository brandRepository;
    private final ShoesRepository shoesRepository;
    private final ShoesSizeRepository shoesSizeRepository;
    private final QuestionRepository csQRepository;
    private final AnswerRepository csARepository;
    private final ImageBridgeComponent imageBridgeComponent;
    private final MemberRepository memberRepository;
    private final BuyRepository buyRepository;
    private final PasswordEncoder passwordEncoder;

    //============== Brand Start ==============
    // Brand 저장
    @Transactional
    public BrandResponse saveBrand(BrandRequest brandRequest, List<MultipartFile> files) {
        Brand brand = BrandRequest.toEntity(brandRequest);
        brandRepository.save(brand);
        imageBridgeComponent.save(ImageType.BRAND, brand.getId(), files);
        return BrandResponse.of(brand);
    }

    // Brand 전체 목록 조회
    public List<BrandResponse> brandFindAll() {
        List<BrandResponse> brands = new ArrayList<>();
        for (Brand brand : brandRepository.findAll()) {
            List<String> fullPath = imageBridgeComponent.findAllFullPath(ImageType.BRAND, brand.getId());
            brands.add(BrandResponse.of(brand, fullPath));
        }
        return brands;
    }

    // Brand 공개 목록 조회
    public List<BrandResponse> brandFindEnable() {
        List<BrandResponse> brands = new ArrayList<>();
        for (Brand brand : brandRepository.findByStatus(StatusCode.ENABLE)) {
            List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.BRAND, brand.getId());
            brands.add(BrandResponse.of(brand, fullPath));
        }
        return brands;
    }

    // Brand 공개 여부 - 공개
    public void brandEnable(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 브랜드가 없습니다."));
        brand.updateStatus(StatusCode.ENABLE);
    }

    // Brand 공개 여부 - 비공개
    public void brandDisable(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 브랜드가 없습니다."));

        // TODO
        // shoesRepository.findByBrandKorName();
        // :: repo에 method 생성해야함
        brand.updateStatus(StatusCode.DISABLE);
    }

    // Brand 상세 조회
    public BrandResponse brandFind(Long id) {

        Brand findBrand = brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand Not Found"));
        List<String> fullPath = imageBridgeComponent.findAllFullPath(ImageType.BRAND, findBrand.getId());

        return BrandResponse.of(findBrand, fullPath);
    }
    //============== Brand End ==============


    //============== Shoes Start ==============
    // Shoes 저장
    @Transactional
    public ShoesResponse saveShoes(ShoesRequest shoesRequest, List<Integer> sizes, List<MultipartFile> files) {
        Shoes shoes = ShoesRequest.toEntity(shoesRequest);
        shoesRepository.save(shoes);

        for (Integer size : sizes) {
            ShoesSize shoesSize = ShoesSize.builder()
                    .size(size)
                    .build();

            shoes.addSize(shoesSizeRepository.save(shoesSize));
        }

        imageBridgeComponent.save(ImageType.SHOES, shoes.getId(), files);

        return ShoesResponse.of(shoes);
    }

    // Shoes 전체 조회
    public List<ShoesResponse> shoesFindAll() {
        List<ShoesResponse> severalShoes = new ArrayList<>();
        for (Shoes shoes : shoesRepository.findAll()) {
            List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, shoes.getId());
            severalShoes.add(ShoesResponse.of(shoes, fullPath));
        }
        return severalShoes;
    }
    //============== Shoes End ==============


    //============== CS Question Start ==============
    // Question 상세 조회
    public QuestionResponse findQuestion(Long id) {
        Question question = csQRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 문의가 없습니다."));

        List<String> fullPath = imageBridgeComponent.findAllFullPath(ImageType.QUESTION, question.getId());

        return QuestionResponse.of(question, fullPath);
    }

    // Question 전체 조회
    public List<QuestionResponse> findQuestionAll() {
        List<QuestionResponse> questions = new ArrayList<>();
        for (Question customerQ : csQRepository.findAll()) {
            List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.QUESTION, customerQ.getId());
            questions.add(QuestionResponse.of(customerQ, fullPath));
        }
        return questions;
    }

    @Transactional
    // Answer 생성
    public QuestionResponse createAnswer(Long id, String content, String email) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("조회된 사용자가 없습니다."));

        Question customerQ = csQRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 문의가 없습니다."));

        Answer csA = Answer.builder()
                .question(customerQ)
                .answerContent(content)
                .member(findMember)
                .build();

        csARepository.save(csA);
        customerQ.getAnswers().add(csA);
        return QuestionResponse.of(customerQ);
    }

    @Transactional
    // Answer 삭제
    public QuestionResponse deleteAnswer(Long id) {
        Answer customerA = csARepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 답변이 없습니다."));

        Question question = customerA.getQuestion();
        csARepository.delete(customerA);

        return QuestionResponse.of(question);
    }
    //============== CS End ==============


    //============== Member Start ==============
    public List<MemberListResponse> getMembers() {
        List<Member> findMembers = memberRepository.findAll();
        List<MemberListResponse> members = new ArrayList<>();

        for (Member member : findMembers) {
            List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.MEMBER, member.getId());
            members.add(MemberListResponse.of(member, fullPath));
        }

        return members;
    }

    public MemberListResponse getMember(Long id) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member Not Found"));
        List<String> fullPath = imageBridgeComponent.findAllFullPath(ImageType.MEMBER, findMember.getId());

        return MemberListResponse.of(findMember, fullPath);
    }

    @Transactional
    public void clearMemberPw(Long id) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member Not Found"));

        findMember.clearPw(passwordEncoder.encode("1234"));
    }

    @Transactional
    public void modifyMember(MemberModifyRequest request) {
        Member findMember = memberRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Member Not Found"));

        findMember.modifyProfile(
                request.getEmail(),
                request.getName(),
                request.getNickname(),
                request.getPhoneNumber(),
                request.getGender(),
                request.getShoesSize());

        log.info("[AdminService.modifyMember] name : " + request.getFiles().get(0).getOriginalFilename());
        log.info("[AdminService.modifyMember] size : " + request.getFiles().size());

        if (request.getFiles().get(0).isEmpty()) {
            log.info("[AdminService.modifyMember] 비어있음! ");
            return;
        }

        if (!imageBridgeComponent.findAllFullPath(ImageType.MEMBER, findMember.getId()).isEmpty()) {
            imageBridgeComponent.delete(ImageType.MEMBER, findMember.getId());
        }

        imageBridgeComponent.save(ImageType.MEMBER, findMember.getId(), request.getFiles());
        log.info("[AdminService.modifyMember] 프로필 이미지 변경함! ");
    }

    @Transactional
    public void memberDelete(Long id) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member Not Found"));

        memberRepository.delete(findMember);
    }
    //============== Member End ==============


    //============== Buy Start ==============
    public List<BuyResponse> buyFindAll() {
        List<Buy> findAll = buyRepository.findAll();
        List<BuyResponse> buys = new ArrayList<>();

        for (Buy buy : findAll) {
            List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, buy.getShoes().getId());
            buys.add(BuyResponse.of(buy,fullPath));
        }

        return buys;
    }

    public void buyDelete(Long id) {
        Buy buy = buyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Data Not Found"));
        buyRepository.delete(buy);
    }
    //============== Buy End ==============
}
