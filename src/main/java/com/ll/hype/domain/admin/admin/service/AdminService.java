package com.ll.hype.domain.admin.admin.service;

import com.ll.hype.domain.brand.brand.dto.BrandRequest;
import com.ll.hype.domain.brand.brand.dto.BrandResponse;
import com.ll.hype.domain.brand.brand.entity.Brand;
import com.ll.hype.domain.brand.brand.repository.BrandRepository;
import com.ll.hype.domain.customer.question.dto.CustomerQResponse;
import com.ll.hype.domain.customer.question.entity.CustomerQ;
import com.ll.hype.domain.customer.question.repository.CsQRepository;
import com.ll.hype.domain.shoes.shoes.dto.ShoesRequest;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.domain.shoes.shoes.repository.ShoesRepository;
import com.ll.hype.domain.shoes.shoes.repository.ShoesSizeRepository;
import com.ll.hype.global.enums.StatusCode;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final BrandRepository brandRepository;
    private final ShoesRepository shoesRepository;
    private final ShoesSizeRepository shoesSizeRepository;
    private final CsQRepository csQRepository;
    private final ImageBridgeComponent imageBridgeComponent;

    //============== Brand Start ==============
    // Brand 저장
    @Transactional
    public BrandResponse saveBrand(BrandRequest brandRequest, List<MultipartFile> files) {
        Brand brand = BrandRequest.toEntity(brandRequest);
        brandRepository.save(brand);
        imageBridgeComponent.save(ImageType.BRAND, brand.getId(), files);
        return BrandResponse.of(brand);
    }

    // Brand 전체 목록 조회
    public List<BrandResponse> brandFindAll() {
        List<BrandResponse> brands = new ArrayList<>();
        for (Brand brand : brandRepository.findAll()) {
            List<String> fullPath = imageBridgeComponent.findAllFullPath(ImageType.BRAND, brand.getId());
            brands.add(BrandResponse.of(brand, fullPath));
        }
        return brands;
    }

    // Brand 공개 목록 조회
    public List<BrandResponse> brandFindEnable() {
        List<BrandResponse> brands = new ArrayList<>();
        for (Brand brand : brandRepository.findByStatus(StatusCode.ENABLE)) {
            List<String> fullPath = imageBridgeComponent.findAllFullPath(ImageType.BRAND, brand.getId());
            brands.add(BrandResponse.of(brand, fullPath));
        }
        return brands;
    }

    // Brand 공개 여부 - 공개
    public void brandEnable(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 브랜드가 없습니다."));
        brand.updateStatus(StatusCode.ENABLE);
    }

    // Brand 공개 여부 - 비공개
    public void brandDisable(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 브랜드가 없습니다."));

        // TODO
        // shoesRepository.findByBrandKorName();
        // :: repo에 method 생성해야함
        brand.updateStatus(StatusCode.DISABLE);
    }

    //============== Brand End ==============


    //============== Shoes Start ==============
    // Shoes 저장
    @Transactional
    public ShoesResponse saveShoes(ShoesRequest shoesRequest, List<Integer> sizes) {
        Shoes shoes = ShoesRequest.toEntity(shoesRequest);
        shoesRepository.save(shoes);

        for (Integer size : sizes) {
            ShoesSize shoesSize = ShoesSize.builder()
                    .shoes(shoes)
                    .size(size)
                    .build();
            shoesSizeRepository.save(shoesSize);
        }

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


    //============== CS Question Start ==============
    // Question 상세 조회
    public CustomerQResponse findQuestion(Long id) {
        CustomerQ question = csQRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 문의가 없습니다."));
        return CustomerQResponse.of(question);
    }

    // Question 전체 조회
    public List<CustomerQResponse> findQuestionAll() {
        List<CustomerQResponse> questions = new ArrayList<>();
        for (CustomerQ customerQ : csQRepository.findAll()) {
            questions.add(CustomerQResponse.of(customerQ));
        }
        return questions;
    }
    //============== CS End Start ==============
}
