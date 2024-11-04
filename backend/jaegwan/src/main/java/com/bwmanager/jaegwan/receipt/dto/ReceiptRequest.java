package com.bwmanager.jaegwan.receipt.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReceiptRequest {

    private Long restaurantId;

    public ReceiptRequest(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
