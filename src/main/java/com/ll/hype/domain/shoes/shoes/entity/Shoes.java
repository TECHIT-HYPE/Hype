package com.ll.hype.domain.shoes.shoes.entity;

import com.ll.hype.global.enums.Gender;
import com.ll.hype.global.enums.StatusCode;
import com.ll.hype.global.jpa.BaseEntity;
import com.ll.hype.domain.brand.brand.entity.Brand;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Shoes extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;

    private String korName; // 한글명
    private String engName; // 영문명
    private String model; // 모델명
    private int price; // 발매가
    private String release; //출시일

    @Enumerated(value = EnumType.STRING)
    private ShoesCategory shoesCategory;

    private String color; // 신발 색상

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Enumerated(value = EnumType.STRING)
    private StatusCode status; // 공개 비공개

    @OneToMany(mappedBy = "shoes", cascade = CascadeType.ALL)
    List<ShoesSize> sizes = new ArrayList<>();
}
