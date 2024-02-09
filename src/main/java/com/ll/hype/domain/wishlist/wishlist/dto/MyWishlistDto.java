package com.ll.hype.domain.wishlist.wishlist.dto;

import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyWishlistDto {
    private Long id;
    private Long brandId;
    private Long shoesId;
    private Long shoesSizeId;

    private String brandEngName;
    private String shoesEngName;
    private int shoesSize;
    private Long instantPrice; // 즉시 구매가 (현재 구매 가능한 최저 판매가)
}
