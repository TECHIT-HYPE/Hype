package com.ll.hype.domain.brand.brand.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ll.hype.domain.brand.brand.entity.Brand;
import com.ll.hype.global.enums.StatusCode;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class BrandRepositoryTest {
    @Autowired
    private BrandRepository brandRepository;

    @Test
    @DisplayName("브랜드 생성")
    public void t1() {
        Brand brand = Brand.builder()
                .korName("나이키")
                .engName("NIKE")
                .build();
        brand.updateStatus(StatusCode.DISABLE);
        Brand saveBrand = brandRepository.save(brand);

        assertAll(
                () -> assertEquals(1, saveBrand.getId(), "ID 일치 확인"),
                () -> assertEquals("나이키", saveBrand.getKorName(), "한글명 일치 확인"),
                () -> assertEquals("NIKE", saveBrand.getEngName(), "영문명 일치 확인")
        );
    }

    @Test
    @DisplayName("브랜드 목록 중 Stauts가 공개인 게시물")
    @Transactional
    public void t2() {
        Brand brandA = Brand.builder()
                .korName("나이키")
                .engName("NIKE")
                .build();
        brandA.updateStatus(StatusCode.DISABLE);
        brandRepository.save(brandA);

        Brand brandB = Brand.builder()
                .korName("아디다스")
                .engName("ADIDAS")
                .build();
        brandB.updateStatus(StatusCode.ENABLE);
        brandRepository.save(brandB);

        List<Brand> findStatusEnable = brandRepository.findByStatus(StatusCode.ENABLE);

        assertAll(
                () -> Assertions.assertThat(findStatusEnable.size()).isEqualTo(1),
                () -> Assertions.assertThat(findStatusEnable.get(0).getKorName()).isEqualTo("아디다스"),
                () -> Assertions.assertThat(findStatusEnable.get(0).getEngName()).isEqualTo("ADIDAS")
        );
    }

}