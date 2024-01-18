package com.ll.hype.domain.shoes.shoes.entity;

import com.ll.hype.global.enums.Gender;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Shoes extends BaseEntity {
    private String brandId;
    private String korName;
    private String engName;
    private String model;
    private int price;
    private String release;
    private String color;
    private Gender gender;
    private String status;

}
