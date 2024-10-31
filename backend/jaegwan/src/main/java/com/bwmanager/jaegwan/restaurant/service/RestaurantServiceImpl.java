package com.bwmanager.jaegwan.restaurant.service;

import com.bwmanager.jaegwan.global.error.ErrorCode;
import com.bwmanager.jaegwan.global.error.exception.RestaurantException;
import com.bwmanager.jaegwan.restaurant.dto.RestaurantResponse;
import com.bwmanager.jaegwan.restaurant.entity.Restaurant;
import com.bwmanager.jaegwan.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public RestaurantResponse getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantException(ErrorCode.RESTAURANT_NOT_FOUND));

        return RestaurantResponse.from(restaurant);

    }

}
