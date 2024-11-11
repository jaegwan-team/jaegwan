package com.bwmanager.jaegwan.receipt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Schema(description = "구매 내역에 있는 재료를 확정 요청하는 DTO")
public class ReceiptIngredientConfirmRequest {

    @Schema(description = "구매 내역을 확정할 receiptIngredientId", example = "1")
    private Long receiptIngredientId;

    @Schema(description = "재료 이름", example = "양파")
    private String name;

    @Schema(description = "재료 카테고리", example = "채소")
    private String category;

    @Schema(description = "재료 양", example = "3.0")
    private double amount;

    @Schema(description = "재료 단위", example = "개")
    private String unit;

    @Schema(description = "재료 가격", example = "3000")
    private int price;

    @Schema(description = "재료 유통기한", example = "2024-11-05")
    private LocalDate expirationDate;
}
