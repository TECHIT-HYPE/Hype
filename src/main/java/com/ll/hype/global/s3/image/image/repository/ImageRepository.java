package com.ll.hype.global.s3.image.image.repository;

import com.ll.hype.global.s3.image.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
