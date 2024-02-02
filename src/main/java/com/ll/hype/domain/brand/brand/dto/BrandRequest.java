package com.ll.hype.domain.brand.brand.dto;

import com.ll.hype.domain.brand.brand.entity.Brand;
import com.ll.hype.global.enums.StatusCode;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandRequest {
    @NotNull
    private String korName;

    @NotNull
    private String engName;

    @NotNull
    private StatusCode status;

    public static Brand toEntity(BrandRequest brandRequest) {
        return Brand.builder()
                .korName(brandRequest.getKorName())
                .engName(brandRequest.getEngName())
                .status(brandRequest.getStatus())
                .build();
    }
}
