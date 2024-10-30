package com.bwmanager.jaegwan.ingredient.dto;

import com.bwmanager.jaegwan.ingredient.entity.Category;
import com.bwmanager.jaegwan.ingredient.entity.Unit;
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
    private Category category;
    private double totalAmount;
    private Unit unit;
    private int leftExpirationDay;
}
