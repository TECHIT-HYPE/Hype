package com.ll.hype.domain.wishlist.wishlist.service;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.mypage.dto.MyWishlistDto;
import com.ll.hype.domain.wishlist.wishlist.entity.Wishlist;
import com.ll.hype.domain.wishlist.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishlistService {
    private final WishlistRepository wishlistRepository;

    public List<MyWishlistDto> getMyWishlist(Long memberId) {
        return wishlistRepository.findMyWishlistDtoByMemberId(memberId);
    }

    public Optional<Wishlist> findById(Long id) {
        return wishlistRepository.findById(id);
    }

    public boolean canAccess(Member member, Wishlist wishlist) {
        return wishlist.getMember().equals(member);
    }

    @Transactional
    public void deleteWishlist(Wishlist wishlist) {
        wishlistRepository.delete(wishlist);
    }
}
