package com.ll.hype.domain.shoes.shoes.entity;

import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.global.enums.Gender;
import com.ll.hype.global.enums.StatusCode;
import com.ll.hype.global.jpa.BaseEntity;
import com.ll.hype.domain.brand.brand.entity.Brand;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Shoes extends BaseEntity {
    @Comment("브랜드 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;

    @Comment("신발 국문 명")
    private String korName; // 한글명

    @Comment("신발 영문 명")
    private String engName; // 영문명

    @Comment("신발 모델 명")
    private String model; // 모델명

    @Comment("신발 발매가")
    private int price; // 발매가

    @Comment("신발 출시일")
    private LocalDate release; //출시일

    @Comment("신발 카테고리")
    @Enumerated(value = EnumType.STRING)
    private ShoesCategory shoesCategory;

    @Comment("신발 색상")
    private String color; // 신발 색상

    @Comment("신발 성별")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Comment("신발 공개 여부")
    @Enumerated(value = EnumType.STRING)
    private StatusCode status; // 공개 비공개

    @Comment("신발 사이즈 목록")
    @Builder.Default
    @OneToMany(mappedBy = "shoes", cascade = CascadeType.ALL)
    List<ShoesSize> sizes = new ArrayList<>();

    public void updateStatus(StatusCode status) {
        this.status = status;
    }

    public void addSize(ShoesSize shoesSize) {
        shoesSize.addShoes(this);
        sizes.add(shoesSize);
    }
}
