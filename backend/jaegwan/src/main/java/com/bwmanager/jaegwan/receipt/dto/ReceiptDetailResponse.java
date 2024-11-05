package com.bwmanager.jaegwan.receipt.dto;

import com.bwmanager.jaegwan.ingredient.entity.Unit;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReceiptDetailResponse {

    private String name;
    private double amount;
    private String unit;

    @QueryProjection
    public ReceiptDetailResponse(String name, double amount, Unit unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit.getDesc();
    }
}
