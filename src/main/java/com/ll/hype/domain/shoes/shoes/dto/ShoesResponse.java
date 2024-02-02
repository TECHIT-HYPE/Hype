package com.ll.hype.domain.shoes.shoes.dto;

import com.ll.hype.domain.brand.brand.entity.Brand;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesCategory;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.global.enums.Gender;
import com.ll.hype.global.enums.StatusCode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private LocalDate release;
    private ShoesCategory shoesCategory;
    private String color;
    private Gender gender;
    private StatusCode status;
    private List<ShoesSize> sizes;
    private List<String> fullPath;

    public static ShoesResponse of(Shoes shoes) {
        return ShoesResponse.builder()
                .id(shoes.getId())
                .createDate(shoes.getCreateDate())
                .modifyDate(shoes.getModifyDate())
                .brand(shoes.getBrand())
                .korName(shoes.getKorName())
                .engName(shoes.getEngName())
                .model(shoes.getModel())
                .price(shoes.getPrice())
                .release(shoes.getRelease())
                .shoesCategory(shoes.getShoesCategory())
                .color(shoes.getColor())
                .gender(shoes.getGender())
                .status(shoes.getStatus())
                .sizes(shoes.getSizes())
                .build();
    }

    public static ShoesResponse of(Shoes shoes, List<String> fullPath) {
        return ShoesResponse.builder()
                .id(shoes.getId())
                .createDate(shoes.getCreateDate())
                .modifyDate(shoes.getModifyDate())
                .brand(shoes.getBrand())
                .korName(shoes.getKorName())
                .engName(shoes.getEngName())
                .model(shoes.getModel())
                .price(shoes.getPrice())
                .release(shoes.getRelease())
                .shoesCategory(shoes.getShoesCategory())
                .color(shoes.getColor())
                .gender(shoes.getGender())
                .status(shoes.getStatus())
                .sizes(shoes.getSizes())
                .fullPath(fullPath)
                .build();
    }
}
