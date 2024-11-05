package com.bwmanager.jaegwan.significant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignificantCreateResponse {

    @Schema(description = "재료 이름", example = "양파")
    private String ingredientName;

    @Schema(description = "재료 수량", example = "15.0")
    private double amount;
}
