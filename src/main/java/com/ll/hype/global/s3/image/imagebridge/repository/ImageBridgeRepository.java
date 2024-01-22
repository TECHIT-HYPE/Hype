package com.ll.hype.global.s3.image.imagebridge.repository;

import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.imagebridge.entity.ImageBridge;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageBridgeRepository extends JpaRepository<ImageBridge, Long> {
    Optional<ImageBridge> findByTypeAndTypeId(ImageType imageType, long typeId);
}
