package com.ll.hype.Hype.domain.wishlist.wishlist.entity;

import com.ll.hype.Hype.domain.member.member.entity.Member;
import com.ll.hype.Hype.global.jpa.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
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
public class WishList extends BaseEntity {
    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "wishList", cascade = CascadeType.ALL)
    List<WishListShoes> wishLists = new ArrayList<>();
}
