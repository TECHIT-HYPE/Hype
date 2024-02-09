package com.ll.hype.domain.wishlist.wishlist.service;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.shoes.shoes.dto.ShoesWishCheckRequest;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.domain.shoes.shoes.repository.ShoesRepository;
import com.ll.hype.domain.shoes.shoes.repository.ShoesSizeRepository;
import com.ll.hype.domain.wishlist.wishlist.dto.MyWishlistDto;
import com.ll.hype.domain.wishlist.wishlist.dto.WishListResponse;
import com.ll.hype.domain.wishlist.wishlist.entity.Wishlist;
import com.ll.hype.domain.wishlist.wishlist.repository.WishlistRepository;
import com.ll.hype.global.exception.custom.EntityNotFoundException;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import java.util.ArrayList;
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
    private final ImageBridgeComponent imageBridgeComponent;
    private final ShoesRepository shoesRepository;
    private final ShoesSizeRepository shoesSizeRepository;

    public void addWishShoes(Long id, int size, Member member) {
        Shoes shoes = shoesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("조회된 데이터가 없습니다."));
        ShoesSize shoesSize = shoesSizeRepository.findByShoesAndSize(shoes, size)
                .orElseThrow(() -> new EntityNotFoundException("조회된 데이터가 없습니다."));

        Optional<Wishlist> findWish = wishlistRepository.findByMemberAndShoesSize(member, shoesSize);

        if (findWish.isPresent()) {
            throw new IllegalArgumentException("이미 자는 아니고 이미 찜목록에 데이터가 존재합니다.");
        }

        Wishlist wish = Wishlist.builder()
                .member(member)
                .shoesSize(shoesSize)
                .build();

        wishlistRepository.save(wish);
    }

    public boolean checkShoesWish(ShoesWishCheckRequest request, Member member) {
        ShoesSize shoesSize = shoesSizeRepository.findByShoesIdAndSize(request.getShoesId(), request.getSize())
                .orElseThrow(() -> new EntityNotFoundException("조회된 데이터가 없습니다."));

        Optional<Wishlist> findWish = wishlistRepository.findByMemberAndShoesSize(member, shoesSize);

        return findWish.isEmpty();
    }

    public List<MyWishlistDto> getMyWishlist(Long memberId) {
        List<MyWishlistDto> myWishlistDtoByMemberId = wishlistRepository.findMyWishlistDtoByMemberId(memberId);

//        for (MyWishlistDto dto : myWishlistDtoByMemberId) {
//            List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, dto.getShoesId());
//            dto.setFullPath(fullPath);
//        }

        return myWishlistDtoByMemberId;
    }

    public List<WishListResponse> myWishList(Member member) {
        List<WishListResponse> wishes = new ArrayList<>();
        List<Wishlist> findWishes = wishlistRepository.findByMember(member);

        for (Wishlist wishlist : findWishes) {
            List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES,
                    wishlist.getShoesSize().getShoes().getId());
            wishes.add(WishListResponse.of(wishlist, fullPath));
        }

        return wishes;
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
