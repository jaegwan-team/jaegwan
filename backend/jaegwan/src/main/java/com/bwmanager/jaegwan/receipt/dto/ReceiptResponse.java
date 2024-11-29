package com.bwmanager.jaegwan.receipt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
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
