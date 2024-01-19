package com.ll.hype.domain.brand.brand.entity;

import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.global.enums.StatusCode;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
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
public class Brand extends BaseEntity {
    private String korName; // 브랜드 한글명
    private String engName; // 브랜드 영문명

    @Enumerated(value = EnumType.STRING)
    private StatusCode status; // 브랜드 상태 (공개, 비공개)

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    List<Shoes> shoes = new ArrayList<>(); // 브랜드에서 가지고 있는 신발

    public void updateStatus(StatusCode status) {
        this.status = status;
    }
}
