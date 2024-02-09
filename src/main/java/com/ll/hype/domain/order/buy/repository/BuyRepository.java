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
            "And b.member.id != :#{#member.id} " +
            "ORDER BY b.shoesSize.size ASC")
    List<Buy> findHighestPriceByShoesId(Shoes shoes, Member member);

    @Query("SELECT b FROM Buy b WHERE b.shoes = :shoes " +
            "AND b.status = 'BIDDING' " +
            "AND b.shoesSize.size = :size " +
            "AND b.price = (SELECT MAX(bb.price) FROM Buy bb " +
            "WHERE bb.shoes = :shoes " +
            "AND bb.shoesSize.size = :size) " +
            "AND b.status = 'BIDDING' " +
            "And b.member.id != :#{#member.id} " +
            "ORDER BY b.createDate ASC")
    Optional<Buy> findHighestPriceBuy(Shoes shoes, int size, Member member);

    Optional<Buy> findByShoesIdAndMemberAndShoesSizeSizeAndStatus(Long id, Member member, int size, Status status);

    @Query("SELECT b FROM Buy b " +
            "ORDER BY b.createDate DESC")
    List<Buy> findByMember(Member member);

    Optional<Buy> findByIdAndMember(Long id, Member member);
}
