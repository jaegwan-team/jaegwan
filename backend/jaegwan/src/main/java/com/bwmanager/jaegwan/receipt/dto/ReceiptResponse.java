package com.bwmanager.jaegwan.receipt.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReceiptResponse {

    private Long id;
    private String mainIngredientName;
    private int leftCount;
    private LocalDateTime createdDate;
    private boolean isConfirmed;

    @QueryProjection
    public ReceiptResponse(Long id, String mainIngredientName, int leftCount, LocalDateTime createdDate, boolean isConfirmed) {
        this.id = id;
        this.mainIngredientName = mainIngredientName;
        this.leftCount = leftCount;
        this.createdDate = createdDate;
        this.isConfirmed = isConfirmed;
    }
}
