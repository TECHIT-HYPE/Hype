package com.ll.hype.domain.shoes.shoes.entity;

import com.ll.hype.domain.brand.brand.entity.Brand;
import com.ll.hype.global.enums.Gender;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
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

    @ManyToOne
    private Brand brand;

    private String korName;
    private String engName;
    private String model;
    private int price;
    private String release; //출시일
    private String color;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    private String status;

    @OneToMany(mappedBy = "shoes", cascade = CascadeType.ALL)
    List<ShoesSize> sizes = new ArrayList<>();


}
