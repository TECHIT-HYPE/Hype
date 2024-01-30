package com.ll.hype.domain.social.profile.dto;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.image.repository.ImageRepository;
import com.ll.hype.global.s3.image.imagebridge.entity.ImageBridge;
import com.ll.hype.global.s3.image.imagebridge.repository.ImageBridgeRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialProfileDto {
    private Long id;
    private String nickname;
    private String profileImage;
    private ImageType type;
    private long typeId;
    public SocialProfileDto EntityToDto(Member member) {
        ImageBridge imageBridge = ImageBridgeRepository.findByTypeAndTypeId()
        return SocialProfileDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .profileImage()
    }
}
