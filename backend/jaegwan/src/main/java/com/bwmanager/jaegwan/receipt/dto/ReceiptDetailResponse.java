package com.bwmanager.jaegwan.receipt.dto;

import com.bwmanager.jaegwan.ingredient.entity.Category;
import com.bwmanager.jaegwan.ingredient.entity.Unit;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ReceiptDetailResponse {

    private Long id;
    private String name;
    private String category;
    private double amount;
    private String unit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate expirationDate;

    @QueryProjection
    public ReceiptDetailResponse(Long id, String name, Category category, double amount, Unit unit, LocalDate expirationDate) {
        this.id = id;
        this.name = name;
        this.category = category.getDesc();
        this.amount = amount;
        this.unit = unit.getDesc();
        this.expirationDate = expirationDate;
    }
}
