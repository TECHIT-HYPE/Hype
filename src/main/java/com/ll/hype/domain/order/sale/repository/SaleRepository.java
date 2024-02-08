package com.ll.hype.domain.order.sale.repository;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.sale.entity.Sale;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT s FROM Sale s " +
            "WHERE s.shoes.id = :#{#shoes.id} " +
            "AND (s.shoesSize, s.price) IN " +
            "(SELECT ss.shoesSize, MIN(ss.price) FROM Sale ss WHERE ss.shoes.id = :#{#shoes.id} GROUP BY ss.shoesSize) "
            +
            "AND s.status = 'BIDDING' " +
            "And s.member.id != :#{#member.id} " +
            "ORDER BY s.shoesSize.size ASC")
    List<Sale> findLowestPriceByShoesId(Shoes shoes, Member member);

    // AND ss.status ='BIDDING'
    @Query("SELECT s FROM Sale s " +
            "WHERE s.shoes = :shoes " +
            "AND s.shoesSize.size = :size " +
            "AND s.price = (SELECT MIN(ss.price) FROM Sale ss WHERE ss.shoes = :shoes AND ss.shoesSize.size = :size) " +
            "And s.member.id != :#{#member.id} " +
            "AND s.status = 'BIDDING'")
    Optional<Sale> findLowestPriceSale(Shoes shoes, int size, Member member);

    Optional<Sale> findByShoesIdAndMemberAndShoesSizeSize(Long id, Member member, int size);

    @Query("SELECT s FROM Sale s " +
            "ORDER BY s.createDate DESC")
    List<Sale> findByMember(Member member);
}
