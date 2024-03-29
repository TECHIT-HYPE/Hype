package com.ll.hype.domain.social.profile.service;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.repository.MemberRepository;
import com.ll.hype.domain.social.follow.repository.FollowRepository;
import com.ll.hype.domain.social.profile.dto.SocialProfileResponse;
import com.ll.hype.domain.social.social.entity.Social;
import com.ll.hype.domain.social.social.repository.SocialRepository;
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
    private final MemberRepository memberRepository;
    private final SocialRepository socialRepository;
    private final FollowRepository followRepository;
    private final ImageBridgeComponent imageBridgeComponent;


    @Transactional
    public SocialProfileResponse findById(Long profileMemberId, Long principalId) {
        Optional<Member> memberOptional = memberRepository.findById(profileMemberId);


        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            String profileImage = getProfileImage(member.getId());
            List<Social> socialList = socialRepository.findByMemberIdOrderByCreateDateDesc(member.getId());
            SocialProfileResponse socialProfileResponse = SocialProfileResponse.builder()
                    .id(member.getId())
                    .nickname(member.getNickname())
                    .email(member.getEmail())
                    .profileImage(profileImage)
                    .socials(socialList)
                    .build();

            Long followCount = followRepository.countByToMemberId(profileMemberId);
            Long feedCount = (long) socialList.size();
            boolean followstate = followRepository.existsByToMemberIdAndFromMemberId(profileMemberId, principalId);
            socialProfileResponse.updateCounts(followCount, feedCount, followstate);

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
        List<String> imagePath = imageBridgeComponent.findAllFullPath(ImageType.MEMBER, memberId);

        if (!imagePath.isEmpty()) {
            imageBridgeComponent.delete(ImageType.MEMBER, memberId);
        }
        imageBridgeComponent.save(ImageType.MEMBER, memberId, profileImageFiles);
    }
}
