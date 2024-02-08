package com.ll.hype.domain.order.order.repository;

import com.ll.hype.domain.order.order.entity.Orders;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByTossId(String tossId);
}
