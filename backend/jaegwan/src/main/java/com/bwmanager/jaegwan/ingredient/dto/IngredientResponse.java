package com.bwmanager.jaegwan.ingredient.dto;

import com.bwmanager.jaegwan.ingredient.entity.Category;
import com.bwmanager.jaegwan.ingredient.entity.Unit;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IngredientResponse {

    private Long id;
    private Category category;
    private double totalAmount;
    private Unit unit;
    private int leftExpirationDay;

    @Builder
    @QueryProjection
    public IngredientResponse(Long id, Category category, double totalAmount, Unit unit, int leftExpirationDay) {
        this.id = id;
        this.category = category;
        this.totalAmount = totalAmount;
        this.unit = unit;
        this.leftExpirationDay = leftExpirationDay;
    }
}
