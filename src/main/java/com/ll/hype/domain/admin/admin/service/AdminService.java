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

@RequiredArgsConstructor
@Service
public class AdminService {
    private final BrandRepository brandRepository;
    private final ShoesRepository shoesRepository;

    //============== Brand Start ==============
    public BrandResponse saveBrand(BrandRequest brandRequest) {
        Brand brand = BrandRequest.toEntity(brandRequest);
        brandRepository.save(brand);
        return BrandResponse.of(brand);
    }

    public List<BrandResponse> brandFindAll() {
        List<BrandResponse> brands = new ArrayList<>();
        for (Brand brand : brandRepository.findAll()) {
            brands.add(BrandResponse.of(brand));
        }
        return brands;
    }

    public List<BrandResponse> brandFindEnable() {
        List<BrandResponse> brands = new ArrayList<>();
        for (Brand brand : brandRepository.findByStatus(StatusCode.ENABLE)) {
            brands.add(BrandResponse.of(brand));
        }
        return brands;
    }
    //============== Brand End ==============

    //============== Shoes Start ==============
    public ShoesResponse saveShoes(ShoesRequest shoesRequest) {
        Shoes shoes = ShoesRequest.toEntity(shoesRequest);
        shoesRepository.save(shoes);
        return ShoesResponse.of(shoes);
    }

    public List<ShoesResponse> shoesFindAll() {
        List<ShoesResponse> severalShoes = new ArrayList<>();
        for (Shoes shoes : shoesRepository.findAll()) {
            severalShoes.add(ShoesResponse.of(shoes));
        }
        return severalShoes;
    }

    //============== Shoes End ==============
}
