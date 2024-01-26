package com.ll.hype.domain.order.buy.repository;

import com.ll.hype.domain.order.buy.entity.Buy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyRepository extends JpaRepository<Buy, Long> {
    List<Buy> findByShoesId(Long id);

}
