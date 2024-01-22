package com.ll.hype.global.s3.image.imagebridge.entity;

import com.ll.hype.global.jpa.BaseEntity;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.image.entity.Image;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
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
public class ImageBridge extends BaseEntity {
    @Enumerated(value = EnumType.STRING)
    private ImageType type;

    private long typeId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Image> images;
}
