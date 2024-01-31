package com.ll.hype.domain.social.profile.dto;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.image.repository.ImageRepository;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import com.ll.hype.global.s3.image.imagebridge.entity.ImageBridge;
import com.ll.hype.global.s3.image.imagebridge.repository.ImageBridgeRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialProfileDto {
    private Long id;
    private String nickname;
    private String profileImage;

    private ImageBridgeComponent imageBridgeComponent;

    public SocialProfileDto entityToDto(Member member) {
        List<String> imageFullPaths = imageBridgeComponent.findOneFullPath(ImageType.MEMBER, member.getId());
        return SocialProfileDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .profileImage(imageFullPaths.isEmpty() ? null : imageFullPaths.get(0))
                .build();
    }

}
