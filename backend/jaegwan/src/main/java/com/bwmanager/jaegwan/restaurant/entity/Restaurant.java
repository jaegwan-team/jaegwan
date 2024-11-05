package com.bwmanager.jaegwan.restaurant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 식당 이름

    @Column(nullable = false)
    private String registerNumber; // 사업자 등록 번호

    private Restaurant(String name, String registerNumber) {
        this.name = name;
        this.registerNumber = registerNumber;
    }

    public static Restaurant from(String name, String registerNumber) {
        return new Restaurant(name, registerNumber);
    }

}
