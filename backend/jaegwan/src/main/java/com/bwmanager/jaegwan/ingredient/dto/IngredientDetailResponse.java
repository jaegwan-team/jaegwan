package com.bwmanager.jaegwan.ingredient.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class IngredientDetailResponse {

    private LocalDateTime purchaseDate;
    private double amount;
    private int leftExpirationDay;

    @QueryProjection
    public IngredientDetailResponse(LocalDateTime purchaseDate, double amount, int leftExpirationDay) {
        this.purchaseDate = purchaseDate;
        this.amount = amount;
        this.leftExpirationDay = leftExpirationDay;
    }
}
