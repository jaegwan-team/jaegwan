package com.bwmanager.jaegwan.significant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignificantCreateResponse {

    private String ingredientName;
    private double amount;
}
