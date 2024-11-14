package com.bwmanager.jaegwan.receipt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "구매 내역에 있는 재료를 확정 요청하는 DTO")
public class ReceiptIngredientConfirmRequest {

    @Schema(description = "구매 내역을 확정할 receiptId", example = "10")
    private Long receiptId;

    @Schema(description = "확정할 재료 정보 List", exampleClasses = {ReceiptIngredientConfirmData.class})
    List<ReceiptIngredientConfirmData> receiptIngredientConfirmData;
}
