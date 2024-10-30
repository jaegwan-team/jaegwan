package com.bwmanager.jaegwan.ingredient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientResponse {

    private Long id;
    private String category;
    private double amount;
    private String unit;
    private int leftExpirationDay;
}
