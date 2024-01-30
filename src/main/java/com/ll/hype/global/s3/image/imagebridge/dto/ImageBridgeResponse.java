package com.ll.hype.global.s3.image.imagebridge.dto;

import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.image.entity.Image;
import com.ll.hype.global.s3.image.imagebridge.entity.ImageBridge;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageBridgeResponse {
    private Long id;
    private ImageType type;
    private long typeId;

    @Builder.Default
    private List<Image> images = new ArrayList<>();

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;


    public static ImageBridgeResponse of(ImageBridge imageBridge) {
        return ImageBridgeResponse.builder()
                .id(imageBridge.getId())
                .type(imageBridge.getType())
                .typeId(imageBridge.getTypeId())
                .images(imageBridge.getImages())
                .createDate(imageBridge.getCreateDate())
                .modifyDate(imageBridge.getModifyDate())
                .build();
    }
}
