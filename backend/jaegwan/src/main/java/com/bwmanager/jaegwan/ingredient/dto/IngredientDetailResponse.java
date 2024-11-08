package com.bwmanager.jaegwan.ingredient.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Schema(description = "재료 상세 조회 응답 DTO")
public class IngredientDetailResponse {

    @Schema(description = "구매 일자", example = "2024-11-08")
    private LocalDateTime purchaseDate;

    @Schema(description = "재료 양", example = "10")
    private double amount;

    @Schema(description = "유통기한까지 남은 일 수", example = "3")
    private int leftExpirationDay;

    @QueryProjection
    public IngredientDetailResponse(LocalDateTime purchaseDate, double amount, int leftExpirationDay) {
        this.purchaseDate = purchaseDate;
        this.amount = amount;
        this.leftExpirationDay = leftExpirationDay;
    }
}
