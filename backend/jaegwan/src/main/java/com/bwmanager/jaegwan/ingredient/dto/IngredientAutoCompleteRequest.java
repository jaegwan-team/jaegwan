package com.bwmanager.jaegwan.ingredient.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "재료 자동완성 요청 DTO")
public class IngredientAutoCompleteRequest {

    @Schema(description = "재료를 조회할 restaurantId", example = "1")
    private Long restaurantId;

    @Schema(description = "검색 단어", example = "감자")
    private String word;
}
