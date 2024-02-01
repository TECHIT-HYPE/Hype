package com.ll.hype.domain.social.profile.service;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.repository.MemberRepository;
import com.ll.hype.domain.social.profile.dto.SocialProfileResponse;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.image.component.ImageComponent;
import com.ll.hype.global.s3.image.image.entity.Image;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import com.ll.hype.global.s3.image.imagebridge.entity.ImageBridge;
import com.ll.hype.global.s3.image.imagebridge.repository.ImageBridgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SocialProfileService {
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final ImageBridgeRepository imageBridgeRepository;
    private final ImageBridgeComponent imageBridgeComponent;
    private final ImageComponent imageComponent;

    @Transactional
    public SocialProfileResponse findById(Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            String profileImage = getProfileImage(member.getId());
            SocialProfileResponse socialProfileResponse = SocialProfileResponse.builder()
                    .id(member.getId())
                    .nickname(member.getNickname())
                    .email(member.getEmail())
                    .profileImage(profileImage)
                    .build();

            return socialProfileResponse;
        } else {
            throw new NoSuchElementException("해당 id에 대한 Member를 찾을 수 없습니다.");
        }
    }
    private String getProfileImage(Long memberId) {
        List<String> imageFullPaths = imageBridgeComponent.findOneFullPath(ImageType.MEMBER, memberId);
        return imageFullPaths.isEmpty() ? null : imageFullPaths.get(0);
    }

    @Transactional
    public void updateProfileImage(Long memberId, List<MultipartFile> profileImageFiles) {
        Optional<ImageBridge> byTypeAndTypeId = imageBridgeRepository.findByTypeAndTypeId(ImageType.SOCIAL, memberId);

        if (byTypeAndTypeId.isPresent()) {
            ImageBridge imageBridge = byTypeAndTypeId.get();
            List<Image> images = imageBridge.getImages();

            // 기존 이미지 삭제
            for (Image image : images) {
                imageComponent.delete(image);
            }

            // 새 이미지 업로드 및 연결
            for (MultipartFile file : profileImageFiles) {
                Image image = imageComponent.upload(file, ImageType.SOCIAL);
                image.updateImageBridge(imageBridge);
                images.add(image);
            }

            // 이미지 브릿지 업데이트
            imageBridge.updateImages(images);
        } else {imageBridgeComponent.save(ImageType.SOCIAL, memberId, profileImageFiles);}
        }

}
