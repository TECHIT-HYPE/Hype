package com.ll.hype.domain.wishlist.wishlist.repository;

import com.ll.hype.domain.wishlist.wishlist.dto.MyWishlistDto;
import com.ll.hype.domain.wishlist.wishlist.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    @Query("SELECT new com.ll.hype.domain.wishlist.wishlist.dto.MyWishlistDto(w.id, b.id, s.id, ss.id, b.engName, s.engName, ss.size, MIN(sr.price)) " +
            "FROM Wishlist w " +
            "JOIN w.shoesSize ss " +
            "JOIN ss.shoes s " +
            "JOIN s.brand b " +
            "LEFT JOIN Sale sr ON sr.shoesSize.id = ss.id AND sr.status = 'BIDDING' " +
            "WHERE w.member.id = :memberId " +
            "GROUP BY b.id, s.id, ss.id, b.engName, s.engName, ss.size " +
            "ORDER BY w.createDate DESC")
    List<MyWishlistDto> findMyWishlistDtoByMemberId(@Param("memberId") Long memberId);
}