package com.ll.hype.domain.admin.admin.service;

import com.ll.hype.domain.brand.brand.dto.BrandRequest;
import com.ll.hype.domain.brand.brand.dto.BrandResponse;
import com.ll.hype.domain.brand.brand.entity.Brand;
import com.ll.hype.domain.brand.brand.repository.BrandRepository;
import com.ll.hype.domain.shoes.shoes.dto.ShoesRequest;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.repository.ShoesRepository;
import com.ll.hype.global.enums.StatusCode;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final BrandRepository brandRepository;
    private final ShoesRepository shoesRepository;

    //============== Brand Start ==============
    // Brand 저장
    @Transactional
    public BrandResponse saveBrand(BrandRequest brandRequest) {
        Brand brand = BrandRequest.toEntity(brandRequest);
        brandRepository.save(brand);
        return BrandResponse.of(brand);
    }

    // Brand 전체 목록 조회
    public List<BrandResponse> brandFindAll() {
        List<BrandResponse> brands = new ArrayList<>();
        for (Brand brand : brandRepository.findAll()) {
            brands.add(BrandResponse.of(brand));
        }
        return brands;
    }

    // Brand 공개 목록 조회
    public List<BrandResponse> brandFindEnable() {
        List<BrandResponse> brands = new ArrayList<>();
        for (Brand brand : brandRepository.findByStatus(StatusCode.ENABLE)) {
            brands.add(BrandResponse.of(brand));
        }
        return brands;
    }

    // Brand 공개 여부 - 공개
    public void brandEnable(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("조회된 브랜드가 없습니다."));
        brand.updateStatus(StatusCode.ENABLE);
    }

    // Brand 공개 여부 - 비공개
    public void brandDisable(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("조회된 브랜드가 없습니다."));

        // shoesRepository.findByBrandKorName();
        // :: repo에 method 생성해야함
        brand.updateStatus(StatusCode.DISABLE);
    }
    //============== Brand End ==============


    //============== Shoes Start ==============
    // Shoes 저장
    @Transactional
    public ShoesResponse saveShoes(ShoesRequest shoesRequest) {
        Shoes shoes = ShoesRequest.toEntity(shoesRequest);
        shoesRepository.save(shoes);
        return ShoesResponse.of(shoes);
    }

    // Shoes 전체 조회
    public List<ShoesResponse> shoesFindAll() {
        List<ShoesResponse> severalShoes = new ArrayList<>();
        for (Shoes shoes : shoesRepository.findAll()) {
            severalShoes.add(ShoesResponse.of(shoes));
        }
        return severalShoes;
    }
    //============== Shoes End ==============
}
