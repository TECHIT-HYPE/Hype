package com.ll.hype.domain.brand.brand.entity;

import com.ll.hype.global.enums.StatusCode;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
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
public class Brand extends BaseEntity {
    private String imagePath;
    private String korName;
    private String engName;
    private StatusCode status;
}
