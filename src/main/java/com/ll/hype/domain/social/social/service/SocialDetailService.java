package com.ll.hype.domain.social.social.service;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.social.social.dto.SocialDetailResponse;
import com.ll.hype.domain.social.social.entity.Social;
import com.ll.hype.domain.social.social.repository.SocialRepository;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import com.ll.hype.global.s3.image.imagebridge.entity.ImageBridge;
import com.ll.hype.global.s3.image.imagebridge.repository.ImageBridgeRepository;
import com.ll.hype.global.security.authentication.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SocialDetailService {
    private final SocialRepository socialRepository;
    private final ImageBridgeComponent imageBridgeComponent;

    public SocialDetailResponse findSocial(Long id) {
        Optional<Social> socialOptional = socialRepository.findById(id);

        if (socialOptional.isPresent()) {
            Social social = socialOptional.get();
            Member member = social.getMember();
            String profileImage = imageBridgeComponent.findOneFullPath(ImageType.MEMBER, member.getId()).get(0);
            List<String> postImages = imageBridgeComponent.findAllFullPath(ImageType.SOCIAL, social.getId());


            return SocialDetailResponse.builder()
                    .socialId(social.getId())
                    .member(member)
                    .content(social.getContent())
                    .profileImage(profileImage)
                    .postImages(postImages)
                    .build();
        } else {
            throw new NoSuchElementException("해당 id에 대한 Social를 찾을 수 없습니다.");
        }
    }
    public void delete(Long socialId) {
        imageBridgeComponent.delete(ImageType.SOCIAL, socialId);
        socialRepository.deleteById(socialId);
    }

    public void update(SocialDetailResponse socialDetailResponse, List<MultipartFile> files) {
        Social social = toEntity(socialDetailResponse);

        socialRepository.save(social);

        imageBridgeComponent.save(ImageType.SOCIAL, social.getId(), files);
    }

    private Social toEntity(SocialDetailResponse socialDetailResponse) {
        return Social.builder()
                .id(socialDetailResponse.getSocialId())
                .member(socialDetailResponse.getMember())
                .content(socialDetailResponse.getContent())
                .build();
    }
}
