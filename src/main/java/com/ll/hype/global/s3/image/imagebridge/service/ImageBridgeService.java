package com.ll.hype.global.s3.image.imagebridge.service;


import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.image.entity.Image;
import com.ll.hype.global.s3.image.image.service.ImageService;
import com.ll.hype.global.s3.image.imagebridge.entity.ImageBridge;
import com.ll.hype.global.s3.image.imagebridge.repository.ImageBridgeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Slf4j
@Service
public class ImageBridgeService {
    private final ImageBridgeRepository imageBridgeRepository;
    private final ImageService imageService;

    public ImageBridge save(ImageType imageType, long typeId, List<MultipartFile> files) {
        List<Image> images = new ArrayList<>();
        for (MultipartFile file : files) {
            Image image = imageService.uploadImage(file, imageType);
            images.add(image);
        }

        ImageBridge imageBridge = ImageBridge.builder()
                .type(imageType)
                .typeId(typeId)
                .build();

        imageBridgeRepository.save(imageBridge);

        return imageBridge;
    }

    public ImageBridge findOne(ImageType imageType, long typeId) {
        Optional<ImageBridge> findOne = imageBridgeRepository.findByTypeAndTypeId(imageType, typeId);

        if (findOne.isEmpty()) {
            throw new IllegalArgumentException("찾는 정보가 없습니다");
        }

        return findOne.get();
    }
}
