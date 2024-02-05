package com.ll.hype.domain.shoes.shoes.repository;

import com.ll.hype.domain.brand.brand.entity.Brand;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesCategory;
import com.ll.hype.global.enums.StatusCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShoesRepository extends JpaRepository<Shoes, Long> {
    List<Shoes> findByStatus(StatusCode status);

    List<Shoes> findByKorNameContainingIgnoreCase(String keyword);

    List<Shoes> findByEngNameContainingIgnoreCase(String keyword);

    @Query("SELECT s FROM Shoes s " +
            "WHERE LOWER(s.korName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.engName) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            "OR LOWER(s.model) LIKE LOWER(CONCAT('%', :keyword, '%'))"+
            "AND s.status = 'ENABLE'")
    List<Shoes> findByShoesKeyword(String keyword);

    @Query("SELECT s FROM Shoes s " +
            "WHERE s.shoesCategory IN :categories AND s.status='ENABLE'")
    List<Shoes> findByCategories(@Param("categories") List<ShoesCategory> categories);
}
