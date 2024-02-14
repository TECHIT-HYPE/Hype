package com.ll.hype.domain.shoes.shoes.repository;

import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoesSizeRepository extends JpaRepository<ShoesSize, Long> {
    Optional<ShoesSize> findByShoesAndSize(Shoes shoes, int size);
    Optional<ShoesSize> findByShoesIdAndSize(Long id, int size);
}
