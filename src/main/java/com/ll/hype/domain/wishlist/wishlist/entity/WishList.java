package com.ll.hype.domain.wishlist.wishlist.entity;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.global.jpa.BaseEntity;
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
public class WishList extends BaseEntity {
    @ManyToOne
    private Member member;

//    @OneToMany(mappedBy = "wishList", cascade = CascadeType.ALL)
//    List<WishList> wishLists = new ArrayList<>();
}
