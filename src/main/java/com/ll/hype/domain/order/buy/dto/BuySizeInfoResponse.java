package com.ll.hype.domain.order.buy.dto;

import com.ll.hype.domain.brand.brand.entity.Brand;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesCategory;
import com.ll.hype.global.enums.Gender;
import com.ll.hype.global.enums.StatusCode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuySizeInfoResponse {
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
    private List<String> fullPath;

    @Builder.Default
    private Map<Integer, Long> sizeMap = new LinkedHashMap<>();

    public static BuySizeInfoResponse of(Shoes shoes, Map<Integer, Long> sizeMap,List<String> fullPath) {
        return BuySizeInfoResponse.builder()
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
                .sizeMap(sizeMap)
                .fullPath(fullPath)
                .build();
    }
}
