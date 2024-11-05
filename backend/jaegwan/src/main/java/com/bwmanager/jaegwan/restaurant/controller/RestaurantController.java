package com.bwmanager.jaegwan.restaurant.controller;

import com.bwmanager.jaegwan.global.dto.CommonResponse;
import com.bwmanager.jaegwan.restaurant.dto.RestaurantRequest;
import com.bwmanager.jaegwan.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Operation(summary = "식당 정보 조회", description = "식당 ID에 해당하는 식당의 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getRestaurant(@PathVariable("id") Long id) {
        CommonResponse<Object> response = CommonResponse.builder()
                .data(restaurantService.getRestaurant(id))
                .message("식당 정보 조회에 성공했습니다.")
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "식당에 속한 사용자 목록 조회", description = "식당에 속한 사용자들의 목록을 조회합니다.")
    @GetMapping("/{id}/member")
    public ResponseEntity<?> getRestaurantMembers(@PathVariable("id") Long id) {
        CommonResponse<Object> response = CommonResponse.builder()
                .data(restaurantService.getRestaurantMembers(id))
                .message("식당에 속한 사용자 목록 조회에 성공했습니다.")
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "식당 등록", description = "식당 정보를 통해 식당을 등록합니다.")
    @PostMapping
    public ResponseEntity<?> createRestaurant(@RequestBody RestaurantRequest request) {
        // TODO: 식당을 등록한 사용자의 정보도 함께 저장해야 한다.

        CommonResponse<Object> response = CommonResponse.builder()
                .data(restaurantService.createRestaurant(request))
                .message("식당 등록에 성공했습니다.")
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "식당에 사용자 추가", description = "식당에 사용자를 추가합니다.")
    @PostMapping("/{restaurantId}/member/{memberId}")
    public ResponseEntity<?> addRestaurantMember(@PathVariable("restaurantId") Long restaurantId,
                                                 @PathVariable("memberId") Long memberId) {
        restaurantService.addRestaurantMember(restaurantId, memberId);

        CommonResponse<Object> response = CommonResponse.builder()
                .message("식당에 사용자를 추가했습니다.")
                .build();

        return ResponseEntity.ok(response);
    }

}
