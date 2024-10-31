package com.bwmanager.jaegwan.restaurant.controller;

import com.bwmanager.jaegwan.global.dto.CommonResponse;
import com.bwmanager.jaegwan.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getRestaurantById(@PathVariable("id") Long id) {
        CommonResponse<Object> response = CommonResponse.builder()
                .data(restaurantService.getRestaurantById(id))
                .message("식당 조회에 성공했습니다.")
                .build();

        return ResponseEntity.ok(response);
    }

}
