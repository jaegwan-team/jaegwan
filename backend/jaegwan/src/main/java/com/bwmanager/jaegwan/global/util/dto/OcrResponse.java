package com.bwmanager.jaegwan.global.util.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OcrResponse {
    private int category;
    private String name;
    private int unit;
    private int amount;
    private int price;
}
