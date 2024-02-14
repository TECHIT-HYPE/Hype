package com.ll.hype.domain.shoes.search.service;

import com.ll.hype.domain.brand.brand.dto.BrandResponse;
import com.ll.hype.domain.brand.brand.entity.Brand;
import com.ll.hype.domain.brand.brand.repository.BrandRepository;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.dto.ShoesSearchResponse;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.repository.ShoesRepository;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ShoesSearchService {
    private final ShoesRepository shoesRepository;
    private final BrandRepository brandRepository;
    private final ImageBridgeComponent imageBridgeComponent;

    // 해야하는것 :  모델명 , 브랜드(한국, 영어 2개있다)
    // 신발 한국 이름 조회
    public ShoesSearchResponse findByKeyword(String keyword) {
        // Shoes 검색 상품을 담는 배열
        List<ShoesResponse> shoess = new ArrayList<>();
        List<Shoes> findByShoesKeyword = shoesRepository.findByShoesKeyword(keyword);

        for (Shoes shoes : findByShoesKeyword) {
            List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, shoes.getId());
            shoess.add(ShoesResponse.of(shoes, fullPath));
        }

        // Brand 검색 상품을 담는 배열
        List<BrandResponse> brands = new ArrayList<>();
        List<Brand> findByBrandKeyword = brandRepository.findByBrandKeywordIgnoreCase(keyword);

        for (Brand brand : findByBrandKeyword) {
            List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.BRAND, brand.getId());
            brands.add(BrandResponse.of(brand, fullPath));
        }

        return ShoesSearchResponse.of(keyword, shoess, brands);
    }

    //신발 영어 이름 조회
//    public List<ShoesResponse> findByEngword(String keyword) {
//        List<ShoesResponse> shoess = new ArrayList<>();
//        List<Shoes> findByEngNames = shoesRepository.findByEngNameContainingIgnoreCase(keyword);
//        for (Shoes shoes : findByEngNames) {
//            shoess.add(ShoesResponse.of(shoes));
//        }
//        return shoess;
//    }

}

