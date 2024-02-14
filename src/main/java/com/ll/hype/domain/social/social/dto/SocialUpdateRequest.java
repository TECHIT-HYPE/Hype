package com.ll.hype.domain.social.social.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SocialUpdateRequest {
    @NotNull
    private Long socialId;
    private String content;
    private List<String> postImages;

    // 수정자 추가
    public void setContent(String content) {
        this.content = content;
    }

    public void setPostImages(List<String> postImages) {
        this.postImages = postImages;
    }
}

