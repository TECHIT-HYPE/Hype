package com.ll.hype.domain.social.social.entity;

import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
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
public class SocialShoes extends BaseEntity {
    @ManyToOne
    private Social socialBoard;

    @ManyToOne
    private Shoes shoes;
}
