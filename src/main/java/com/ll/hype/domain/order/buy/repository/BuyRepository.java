package com.ll.hype.domain.order.buy.repository;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.global.enums.Status;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyRepository extends JpaRepository<Buy, Long> {
    List<Buy> findByShoesId(Long id);

    @Query("SELECT b FROM Buy b WHERE b.shoes.id = :#{#shoes.id} " +
            "AND b.status = 'BIDDING' " +
            "AND (b.shoesSize, b.price) IN " +
            "(SELECT bb.shoesSize, MAX(bb.price) FROM Buy bb " +
            "WHERE bb.shoes.id = :#{#shoes.id} " +
            "AND bb.status = 'BIDDING' " +
            "GROUP BY bb.shoesSize) " +
            "ORDER BY b.shoesSize.size ASC")
    List<Buy> findHighestPriceByShoesId(Shoes shoes);

    @Query("SELECT b FROM Buy b WHERE b.shoes = :shoes " +
            "AND b.status = 'BIDDING' " +
            "AND b.shoesSize.size = :size " +
            "AND b.price = (SELECT MAX(bb.price) FROM Buy bb " +
            "WHERE bb.shoes = :shoes " +
            "AND bb.shoesSize.size = :size)")
    Buy findHighestPriceBuy(@Param("shoes") Shoes shoes, @Param("size") int size);

    Optional<Buy> findByShoesIdAndMemberAndShoesSizeSizeAndStatus(Long id, Member member, int size, Status status);

    List<Buy> findByMember(Member member);

    Optional<Buy> findByIdAndMember(Long id, Member member);
}
