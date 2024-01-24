package com.ll.hype.domain.order.order.repository;

import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

}