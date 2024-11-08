package com.bwmanager.jaegwan.ingredient.dto;

import com.bwmanager.jaegwan.ingredient.entity.Category;
import com.bwmanager.jaegwan.ingredient.entity.Unit;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@NoArgsConstructor
@Schema(description = "재료 종류 조회 응답 DTO")
public class IngredientResponse {

    @Schema(description = "재료 종류 ID", example = "1")
    private Long id;

    @Schema(description = "재료 이름", example = "고기")
    private String name;

    @Schema(description = "재료 카테고리", example = "육류")
    private Category category;

    @Schema(description = "재료 총량", example = "100")
    private double totalAmount;

    @Schema(description = "재료 단위", example = "g")
    private String unit;

    @Schema(description = "재료 유통기한까지 남은 일 수", example = "3")
    private int leftExpirationDay;

    @QueryProjection
    public IngredientResponse(Long id, String name, Category category, double totalAmount, Unit unit, LocalDateTime expirationDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.totalAmount = totalAmount;
        this.unit = unit.getDesc();
        this.leftExpirationDay = (int) ChronoUnit.DAYS.between(LocalDateTime.now(), expirationDate);;
    }
}
