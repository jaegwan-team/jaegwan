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
@Schema(description = "등록된 재료 조회 요청 DTO")
public class IngredientRequest {

    @Schema(description = "재료를 조회할 restaurantId", example = "1")
    private Long restaurantId;
}
