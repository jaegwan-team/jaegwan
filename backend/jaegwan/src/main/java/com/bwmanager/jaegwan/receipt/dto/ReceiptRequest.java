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

    public ReceiptRequest(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
