package com.ll.hype.domain.social.social.dto;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.social.social.entity.Social;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialUploadRequest {
    private String content;


    public static class SocialUploadRequestBuilder {
        // content 필드의 값을 설정하는 메서드
        public SocialUploadRequestBuilder content(String content) {
            this.content = content;
            return this;
        }
    }
    @Override
    public String toString() {
        return "SocialUploadRequest{" +
                "content='" + content + '\'' +
                '}';
    }
}
