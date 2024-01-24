package com.ll.hype.domain.shoes.shoes.repository;

import com.ll.hype.domain.brand.brand.entity.Brand;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.global.enums.StatusCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoesRepository extends JpaRepository<Shoes, Long> {
    List<Shoes> findByStatus(StatusCode status);
    List<Shoes> findByKorNameContainingIgnoreCase(String keyword);
    List<Shoes> findByEngNameContainingIgnoreCase(String keyword);

}
