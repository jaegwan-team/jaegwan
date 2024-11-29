package com.bwmanager.jaegwan.restaurant.dto;

import com.bwmanager.jaegwan.restaurant.entity.Restaurant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "식당 정보 DTO")
public class RestaurantResponse {

    @Schema(description = "식당 ID", example = "1")
    private Long id;

    @Schema(description = "식당 이름", example = "재관식당")
    private String name;

    @Schema(description = "사업자 등록 번호", example = "000-00-00001")
    private String registerNumber;

    public static RestaurantResponse from(final Restaurant restaurant) {
        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .registerNumber(restaurant.getRegisterNumber())
                .build();
    }

}
