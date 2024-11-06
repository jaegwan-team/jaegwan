package com.bwmanager.jaegwan.significant.dto;

import com.bwmanager.jaegwan.ingredient.entity.Ingredient;
import com.bwmanager.jaegwan.restaurant.entity.Restaurant;
import com.bwmanager.jaegwan.significant.entity.Significant;
import com.bwmanager.jaegwan.significant.entity.SignificantIngredient;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "특이사항 관련 정보 생성 요청 DTO")
public class SignificantCreateRequest {

    @Schema(description = "특이사항 세부 내용", example = "재료 부족으로 인해 추가 주문 필요")
    private String detail;

    @Schema(description = "레스토랑 ID", example = "1", required = true)
    private Long restaurantId;

    @Schema(description = "재료 이름", example = "양파", required = true)
    private String ingredientName;

    @Schema(description = "재료 수량", example = "15.0", required = true)
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
