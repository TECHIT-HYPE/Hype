package com.ll.hype.domain.brand.brand.service;

import com.ll.hype.domain.brand.brand.dto.BrandRequest;
import com.ll.hype.domain.brand.brand.dto.BrandResponse;
import com.ll.hype.domain.brand.brand.entity.Brand;
import com.ll.hype.domain.brand.brand.repository.BrandRepository;
import com.ll.hype.global.enums.StatusCode;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandResponse save(BrandRequest brandRequest) {
        Brand brand = BrandRequest.toEntity(brandRequest);
        brand.updateStatus(StatusCode.ENABLE);
        brandRepository.save(brand);
        return BrandResponse.of(brand);
    }

    public List<BrandResponse> findAll() {
        List<BrandResponse> brands = new ArrayList<>();
        for (Brand brand : brandRepository.findAll()) {
            brands.add(BrandResponse.of(brand));
        }
        return brands;
    }
}
