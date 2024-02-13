package com.ll.hype.domain.shoes.shoes.dto;

import com.ll.hype.domain.brand.brand.dto.BrandResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoesSearchResponse {
    private String keyword;

    @Builder.Default
    List<BrandResponse> brands = new ArrayList<>();

    @Builder.Default
    List<ShoesResponse> shoes = new ArrayList<>();

    public static ShoesSearchResponse of(String keyword,List<ShoesResponse> shoes, List<BrandResponse> brands) {
        return ShoesSearchResponse.builder()
                .keyword(keyword)
                .brands(brands)
                .shoes(shoes)
                .build();
    }
}
