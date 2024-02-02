package com.ll.hype.domain.order.buy.repository;

import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BuyRepository extends JpaRepository<Buy, Long> {
    List<Buy> findByShoesId(Long id);

//    List<Buy> findByShoesIdAndShoesSize_Size(long id, int shoesSize);
//
//    List<Buy> findByShoesIdAndSize(long id, int shoesSize);
}
