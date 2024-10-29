package com.bwmanager.jaegwan.ingredient.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Convert(converter = CategoryConverter.class)
    private Category category;

    @Convert(converter = UnitConverter.class)
    private Unit unit;

    //TODO: 식당 FK 추가

}
