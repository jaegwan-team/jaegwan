package com.bwmanager.jaegwan.ingredient.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "재료 자동완성 응답 DTO")
public class IngredientAutoCompleteResponse {

    @Schema(description = "자동 완성 결과")
    private List<String> ingredientNames;

    public IngredientAutoCompleteResponse(List<String> ingredientNames) {
        this.ingredientNames = ingredientNames;
    }
}
