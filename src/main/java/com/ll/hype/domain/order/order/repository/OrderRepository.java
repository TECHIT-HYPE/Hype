package com.ll.hype.domain.order.order.repository;
import com.ll.hype.domain.order.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;



public interface OrderRepository extends JpaRepository<Orders, Long> {



}
