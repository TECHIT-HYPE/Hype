package com.ll.hype.domain.wishlist.wishlist.dto;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.wishlist.wishlist.entity.Wishlist;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishListResponse {
    private Long id;
    private Member member;
    private Shoes shoes;
    private int size;

    @Builder.Default
    private List<String> fullPath = new ArrayList<>();

    public static WishListResponse of(Wishlist wishlist, List<String> fullPath) {
        return WishListResponse.builder()
                .id(wishlist.getId())
                .member(wishlist.getMember())
                .shoes(wishlist.getShoesSize().getShoes())
                .size(wishlist.getShoesSize().getSize())
                .fullPath(fullPath)
                .build();
    }
}
