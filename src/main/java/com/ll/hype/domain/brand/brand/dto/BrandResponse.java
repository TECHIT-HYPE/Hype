package com.ll.hype.domain.brand.brand.dto;

import com.ll.hype.domain.brand.brand.entity.Brand;
import com.ll.hype.domain.brand.brand.repository.BrandRepository;
import com.ll.hype.global.enums.StatusCode;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponse {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String korName;
    private String engName;
    private StatusCode status;

    public static BrandResponse of(Brand brand) {
        return BrandResponse.builder()
                .id(brand.getId())
                .createDate(brand.getCreateDate())
                .modifyDate(brand.getModifyDate())
                .korName(brand.getKorName())
                .engName(brand.getEngName())
                .status(brand.getStatus())
                .build();
    }

}
