package com.ll.hype.global.jpa.image;

import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.*;
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

    private Long typeId;

    @OneToOne
    private Image image;

    private int index;
}
