package com.bwmanager.jaegwan.restaurant.service;

import com.bwmanager.jaegwan.restaurant.dto.RestaurantResponse;

import java.util.Optional;

public interface RestaurantService {

    /**
     * 식당의 정보를 조회한다.
     * @param id 식당 ID
     * @return 식당 ID에 해당하는 식당 정보
     */
    RestaurantResponse getRestaurantById(Long id);

}
