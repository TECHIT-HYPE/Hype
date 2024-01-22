package com.ll.hype.global.s3.image.image.controller;

import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.image.entity.Image;
import com.ll.hype.global.s3.image.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@Controller
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/upload")
    public Image uploadFile(MultipartFile multipartFile, ImageType imageType) {
        return imageService.uploadImage(multipartFile, imageType);
    }

    @PostMapping("/delete")
    public boolean deleteFile(long id) {
        return imageService.removeImage(id);
    }

    @GetMapping
    public String findFullPath(long id) {
        return imageService.findFullPath(id);
    }
}
