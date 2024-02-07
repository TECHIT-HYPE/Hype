package com.ll.hype.domain.shoes.shoes.entity;

import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ShoesSize extends BaseEntity {
    @Comment("신발 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    private Shoes shoes;

    @Comment("신발 사이즈")
    private int size;

    public void addShoes(Shoes shoes) {
        this.shoes = shoes;
    }
}
