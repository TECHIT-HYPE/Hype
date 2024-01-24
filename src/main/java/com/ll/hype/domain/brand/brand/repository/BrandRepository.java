package com.ll.hype.domain.brand.brand.repository;

import com.ll.hype.domain.brand.brand.entity.Brand;
import com.ll.hype.global.enums.StatusCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findByStatus(StatusCode status);
}
