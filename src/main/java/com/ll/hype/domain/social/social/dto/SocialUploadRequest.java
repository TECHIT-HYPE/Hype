package com.ll.hype.domain.social.social.dto;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.social.social.entity.Social;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class SocialUploadRequest {
    private String content;
}
