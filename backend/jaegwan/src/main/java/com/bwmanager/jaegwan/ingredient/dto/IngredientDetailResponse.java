package com.bwmanager.jaegwan.ingredient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientDetailResponse {

    private LocalDateTime purchaseDate;
    private double amount;
    private long leftExpirationDay;
}
