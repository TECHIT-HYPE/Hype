package com.ll.hype.domain.order.order.repository;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.order.entity.Orders;

import java.util.List;
import java.util.Optional;

import com.ll.hype.domain.order.sale.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByTossId(String tossId);

    @Query("SELECT o FROM Orders o WHERE o.status ='TRADING' "+
            "ORDER BY o.orderDate DESC")
    List<Orders> findTradingByMember(Member member);

    @Query("SELECT o FROM Orders o WHERE o.paymentStatus = 'COMPLETE_PAYMENT' " +
            "ORDER BY o.orderDate DESC")
    Optional<Orders> findByIdAndSaleMember(Long id, Member member);
    @Query("SELECT o FROM Orders o " +
            "WHERE o.sale.member.id = :#{#member.id} "+
            "ORDER BY o.createDate DESC")
    List<Orders> findBySaleMember(Member member);

    @Query("SELECT o FROM Orders o " +
            "WHERE o.buy.shoes.id = :shoesId " +
            "ORDER BY o.orderDate DESC")
//    "AND (o.status = 'TRADE_COMPLETE') " +
    Optional<Orders> findByShoesId(@Param("shoesId") long shoesId);
}
