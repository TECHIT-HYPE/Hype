package com.ll.hype.Hype.domain.wishlist.wishlist.entity;

import com.ll.hype.Hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.Hype.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class WishListShoes extends BaseEntity {
    @ManyToOne
    private WishList wishList;

    @ManyToOne
    private Shoes shoes;
    private int shoesSize;
}
