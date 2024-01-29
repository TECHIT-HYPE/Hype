package com.ll.hype.global.s3.image.image.entity;

import com.ll.hype.global.jpa.BaseEntity;
import com.ll.hype.global.s3.image.imagebridge.entity.ImageBridge;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {
    private String keyName;
    private int index;
    @ManyToOne(fetch = FetchType.LAZY)
    ImageBridge imageBridge;

    public void updateImageBridge(ImageBridge imageBridge) {
        this.imageBridge = imageBridge;
    }
}
