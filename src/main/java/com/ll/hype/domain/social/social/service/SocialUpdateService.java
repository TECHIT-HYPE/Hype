package com.ll.hype.domain.social.social.service;


import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.repository.MemberRepository;
import com.ll.hype.domain.social.social.dto.SocialUpdateRequest;
import com.ll.hype.domain.social.social.dto.SocialUploadRequest;
import com.ll.hype.domain.social.social.entity.Social;
import com.ll.hype.domain.social.social.repository.SocialRepository;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.image.component.ImageComponent;
import com.ll.hype.global.s3.image.image.entity.Image;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import com.ll.hype.global.s3.image.imagebridge.entity.ImageBridge;
import com.ll.hype.global.s3.image.imagebridge.repository.ImageBridgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SocialUpdateService {

    private final MemberRepository memberRepository;
    private final SocialRepository socialRepository;
    private final ImageBridgeComponent imageBridgeComponent;
    private final ImageBridgeRepository imageBridgeRepository;
    private final ImageComponent imageComponent;

    @Transactional
    public void update(Long id,
                       List<MultipartFile> files,
                       SocialUpdateRequest socialUpdateRequest,
                       String memberEmail) {
        Optional<Member> socialMember = memberRepository.findByEmail(memberEmail);
        Member member = socialMember.orElseThrow(() -> new RuntimeException("Member not found with email: " + memberEmail));

        Optional<Social> optionalSocial = socialRepository.findById(id);
        Social social = optionalSocial.orElseThrow(() -> new RuntimeException("Social not found with ID: " + id));

        // 내용(content) 업데이트
        social.updateSocial(socialUpdateRequest.getContent());

        Optional<ImageBridge> imageBridgeOptional = imageBridgeRepository.findByTypeAndTypeId(ImageType.SOCIAL, social.getId());

        if(imageBridgeOptional.isPresent()) {
            ImageBridge imageBridge = imageBridgeOptional.get();
            List<Image> combinedImages = imageBridge.getImages();

            for (MultipartFile file : files) {
                Image image = imageComponent.upload(file, ImageType.SOCIAL);
                image.updateImageBridge(imageBridge);
                combinedImages.add(image);
            }
            imageBridge.updateImages(combinedImages);
        }
    }
}
