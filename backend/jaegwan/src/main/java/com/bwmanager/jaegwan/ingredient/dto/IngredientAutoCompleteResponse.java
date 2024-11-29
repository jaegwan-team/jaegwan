package com.bwmanager.jaegwan.ingredient.dto;

import com.bwmanager.jaegwan.ingredient.entity.Category;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "재료 자동완성 응답 DTO")
public class IngredientAutoCompleteResponse {

    @Schema(description = "재료 이름")
    private String ingredientName;

    @Schema(description = "재료 카테고리")
    private String category;

    @QueryProjection
    public IngredientAutoCompleteResponse(String ingredientName, Category category) {
        this.ingredientName = ingredientName;
        this.category = category.getDesc();
    }
}
