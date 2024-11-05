package com.bwmanager.jaegwan.restaurant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "식당 정보 요청 DTO")
public class RestaurantRequest {

    @Schema(description = "식당 이름", example = "재관식당")
    private String name;

    @Schema(description = "사업자 등록 번호", example = "000-00-00001")
    private String registerNumber;

}
