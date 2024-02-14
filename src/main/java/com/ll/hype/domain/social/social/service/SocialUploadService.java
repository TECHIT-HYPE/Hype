package com.ll.hype.domain.social.social.service;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.repository.MemberRepository;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.repository.ShoesRepository;
import com.ll.hype.domain.social.social.dto.SocialShoesRequest;
import com.ll.hype.domain.social.social.dto.SocialUploadRequest;
import com.ll.hype.domain.social.social.entity.Social;
import com.ll.hype.domain.social.social.entity.SocialShoes;
import com.ll.hype.domain.social.social.repository.SocialRepository;
import com.ll.hype.domain.social.social.repository.SocialShoesRepository;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SocialUploadService {
    private final SocialRepository socialRepository;
    private final MemberRepository memberRepository;
    private final ImageBridgeComponent imageBridgeComponent;
    private final ShoesRepository shoesRepository;
    private final SocialShoesRepository socialShoesRepository;

    @Transactional
    public Social upload(SocialUploadRequest socialUploadRequest, List<MultipartFile> files, String memberEmail) {
        Social social = toEntity(socialUploadRequest, memberEmail);
        socialRepository.save(social);

        imageBridgeComponent.save(ImageType.SOCIAL, social.getId(), files);

        return social;
    }
    public void tagUpload(SocialShoesRequest socialShoesRequest, Long socialId) {
        SocialShoes socialShoes = shoesToEntity(socialShoesRequest, socialId);
        socialShoesRepository.save(socialShoes);
    }



    private Social toEntity(SocialUploadRequest socialUploadRequest, String memberEmail) {
        Optional<Member> socialMember = memberRepository.findByEmail(memberEmail);
        Member member = socialMember.orElseThrow(() -> new RuntimeException("Member not found with email: " + memberEmail));

        return Social.builder()
                .member(member)
                .content(socialUploadRequest.getContent())
                .build();
    }
    private SocialShoes shoesToEntity(SocialShoesRequest socialShoesRequest, Long socialId) {
        Optional<Social> socialOptional = socialRepository.findById(socialId);
        Optional<Shoes> shoesOptional = shoesRepository.findById(socialShoesRequest.getShoesId());
        if (socialOptional.isPresent() && shoesOptional.isPresent()) {
            Social social = socialOptional.get();
            Shoes shoes = shoesOptional.get();
            return SocialShoes.builder()
                    .shoes(shoes)
                    .social(social)
                    .build();

        } else { throw new IllegalArgumentException("Social not found for id: " + socialId);}
    }
}
