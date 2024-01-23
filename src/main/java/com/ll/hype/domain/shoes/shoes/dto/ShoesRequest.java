package com.ll.hype.domain.shoes.shoes.dto;

import com.ll.hype.domain.brand.brand.entity.Brand;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesCategory;
import com.ll.hype.global.enums.Gender;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoesRequest {
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

    public static Shoes toEntity(ShoesRequest shoesRequest) {
        return Shoes.builder()
                .brand(shoesRequest.getBrand())
                .korName(shoesRequest.getKorName())
                .engName(shoesRequest.getEngName())
                .model(shoesRequest.getModel())
                .price(shoesRequest.getPrice())
                .release(shoesRequest.getRelease())
                .shoesCategory(shoesRequest.getShoesCategory())
                .color(shoesRequest.getColor())
                .gender(shoesRequest.getGender())
                .build();
    }
}
