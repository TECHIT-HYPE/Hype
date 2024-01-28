package com.ll.hype.domain.wishlist.wishlist.service;

import com.ll.hype.domain.member.mypage.dto.MyWishlistDto;
import com.ll.hype.domain.wishlist.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishlistService {
    private final WishlistRepository wishlistRepository;

    public List<MyWishlistDto> getMyWishlist(Long memberId) {
        return wishlistRepository.findMyWishlistDtoByMemberId(memberId);
    }
}
