package com.ll.hype.domain.order.order.repository;
import com.ll.hype.domain.order.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;



public interface OrderRepository extends JpaRepository<Order, Long> {



}