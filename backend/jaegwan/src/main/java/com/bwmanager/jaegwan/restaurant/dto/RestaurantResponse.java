package com.bwmanager.jaegwan.restaurant.dto;

import com.bwmanager.jaegwan.restaurant.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestaurantResponse {

    private Long id;
    private String name;
    private String registerNumber;

    public static RestaurantResponse from(Restaurant restaurant) {
        return new RestaurantResponse(restaurant.getId(), restaurant.getName(), restaurant.getRegisterNumber());
    }

}
