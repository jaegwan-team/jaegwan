package com.bwmanager.jaegwan.significant.dto;

import com.bwmanager.jaegwan.ingredient.entity.Ingredient;
import com.bwmanager.jaegwan.restaurant.entity.Restaurant;
import com.bwmanager.jaegwan.significant.entity.Significant;
import com.bwmanager.jaegwan.significant.entity.SignificantIngredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignificantCreateRequest {

    private String detail;
    private Long restaurantId;
    private String ingredientName;
    private double amount;

    public Significant toSignificant(Restaurant restaurant) {
        return Significant.builder()
                .detail(detail)
                .restaurant(restaurant)
                .isConfirmed(false)
                .build();
    }

    public SignificantCreateResponse toSignificantCreateResponse() {
        return SignificantCreateResponse.builder()
                .ingredientName(ingredientName)
                .amount(amount)
                .build();
    }

    public SignificantIngredient toSignificantIngredient(Significant significant, Ingredient ingredient) {
        return SignificantIngredient.builder()
                .amount(amount)
                .ingredient(ingredient)
                .significant(significant)
                .build();
    }
}
