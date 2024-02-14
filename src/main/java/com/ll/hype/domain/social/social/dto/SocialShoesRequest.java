package com.ll.hype.domain.social.social.dto;

import com.ll.hype.domain.social.social.entity.Social;
import com.ll.hype.domain.social.social.entity.SocialShoes;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SocialShoesRequest {
    private Long shoesId;
    private String shoesImage;
    private String shoesName;

    public SocialShoesRequest(Long shoesId, String shoesImage, String shoesName) {
        this.shoesId = shoesId;
        this.shoesImage = shoesImage;
        this.shoesName = shoesName;
    }
}
