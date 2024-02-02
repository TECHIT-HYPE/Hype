package com.ll.hype.domain.brand.brand.service;

import com.ll.hype.domain.brand.brand.dto.BrandResponse;
import com.ll.hype.domain.brand.brand.entity.Brand;
import com.ll.hype.domain.brand.brand.repository.BrandRepository;
import com.ll.hype.global.enums.StatusCode;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BrandService {
    private final BrandRepository brandRepository;
    private final ImageBridgeComponent imageBridgeComponent;

    // 관리자 브랜드 전체 조회 (ENABLE 조회 조회)
    public List<BrandResponse> findAll() {
        List<BrandResponse> brands = new ArrayList<>();
        for (Brand brand : brandRepository.findByStatus(StatusCode.ENABLE)) {
            List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.BRAND, brand.getId());
            brands.add(BrandResponse.of(brand, fullPath));
        }
        return brands;
    }
}
