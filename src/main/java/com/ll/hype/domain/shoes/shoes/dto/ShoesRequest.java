package com.ll.hype.domain.shoes.shoes.dto;

import com.ll.hype.domain.brand.brand.entity.Brand;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesCategory;
import com.ll.hype.global.enums.Gender;
import com.ll.hype.global.enums.StatusCode;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoesRequest {
    private Brand brand;
    private String korName;
    private String engName;
    private String model;
    private int price;
    private LocalDate release;
    private ShoesCategory shoesCategory;
    private String color;
    private Gender gender;
    private StatusCode status;

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
                .status(shoesRequest.getStatus())
                .build();
    }
}
