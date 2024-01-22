package com.ll.hype.global.s3.image.imagebridge.controller;

import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.imagebridge.entity.ImageBridge;
import com.ll.hype.global.s3.image.imagebridge.service.ImageBridgeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
public class ImageBridgeController {
    private final ImageBridgeService imageBridgeService;

    public ImageBridge save(ImageType imageType,
                            long typeId,
                            List<MultipartFile> files) {

        imageBridgeService.save(imageType, typeId, files);
        return ImageBridge.builder()
                .build();
    }

    public ImageBridge findOne(ImageType imageType,
                               long typeId) {

        return imageBridgeService.findOne(imageType, typeId);
    }


}
