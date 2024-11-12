package com.bwmanager.jaegwan.receipt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "restaurantId로 구매 내역 목록을 조회 요청하는 DTO")
public class ReceiptRequest {

    @Schema(description = "구매 내역을 조회할 restaurantId", example = "1")
    private Long restaurantId;

    @Schema(description = "true: 모든 구매 내역 조회, false: 확정되지 않은 구매 내역 조회", example = "true")
    private boolean isAll;

    public ReceiptRequest(Long restaurantId, boolean isAll) {
        this.restaurantId = restaurantId;
        this.isAll = isAll;
    }
}
