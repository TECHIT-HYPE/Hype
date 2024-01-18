package com.ll.hype.domain.shoes.shoes.entity;

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
public class ShoesSize extends BaseEntity {
    private String shoesId;
    private int size;

}
