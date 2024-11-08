package com.bwmanager.jaegwan.ingredient.dto;

import com.bwmanager.jaegwan.ingredient.entity.Category;
import com.bwmanager.jaegwan.ingredient.entity.Unit;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "재료 종류 조회 응답 DTO")
public class IngredientResponse {

    @Schema(description = "재료 종류 ID", example = "1")
    private Long id;

    @Schema(description = "재료 카테고리", example = "육류")
    private Category category;

    @Schema(description = "재료 총량", example = "100")
    private double totalAmount;

    @Schema(description = "재료 단위", example = "g")
    private String unit;

    @Schema(description = "재료 유통기한까지 남은 일 수", example = "3")
    private int leftExpirationDay;

    @QueryProjection
    public IngredientResponse(Long id, Category category, double totalAmount, Unit unit, int leftExpirationDay) {
        this.id = id;
        this.category = category;
        this.totalAmount = totalAmount;
        this.unit = unit.getDesc();
        this.leftExpirationDay = leftExpirationDay;
    }
}
