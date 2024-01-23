package com.ll.hype.domain.shoes.shoes.dto;

import com.ll.hype.domain.brand.brand.entity.Brand;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesCategory;
import com.ll.hype.global.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoesResponse {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private Brand brand;
    private String korName;
    private String engName;
    private String model;
    private int price;
    private String release;
    private ShoesCategory shoesCategory;
    private String color;
    private Gender gender;

    public static ShoesResponse of(Shoes shoes) {
        return ShoesResponse.builder()
                .id(shoes.getId())
                .brand(shoes.getBrand())
                .createDate(shoes.getCreateDate())
                .modifyDate(shoes.getModifyDate())
                .korName(shoes.getKorName())
                .engName(shoes.getEngName())
                .model(shoes.getModel())
                .price(shoes.getPrice())
                .release(shoes.getRelease())
                .shoesCategory(shoes.getShoesCategory())
                .color(shoes.getColor())
                .gender(shoes.getGender())
                .build();
    }

}