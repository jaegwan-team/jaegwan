package com.bwmanager.jaegwan.significant.dto;

import com.bwmanager.jaegwan.restaurant.entity.Restaurant;
import com.bwmanager.jaegwan.significant.entity.Significant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignificantCreateRequest {

    private String detail;

    private Long restaurantId;

    public Significant toEntity(Restaurant restaurant) {
        return Significant.builder()
                .isConfirmed(false)
                .detail(detail)
                .restaurant(restaurant)
                .build();
    }
}
