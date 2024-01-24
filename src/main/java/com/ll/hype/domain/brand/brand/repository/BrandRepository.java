package com.ll.hype.domain.brand.brand.repository;

import com.ll.hype.domain.brand.brand.entity.Brand;
import com.ll.hype.global.enums.StatusCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findByStatus(StatusCode status);

    @Query("SELECT b FROM Brand b " +
            "WHERE LOWER(b.korName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(b.engName) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            "AND b.status = 'ENABLE'")
    List<Brand> findByBrandKeywordIgnoreCase(String keyword);
}
