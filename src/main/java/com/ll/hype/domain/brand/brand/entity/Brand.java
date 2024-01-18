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
    private String korName;
    private String engName;

    @Enumerated(value = EnumType.STRING)
    private StatusCode status;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    List<Shoes> shoes = new ArrayList<>();
}
