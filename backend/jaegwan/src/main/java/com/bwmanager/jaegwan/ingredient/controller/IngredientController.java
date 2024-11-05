package com.bwmanager.jaegwan.ingredient.controller;

import com.bwmanager.jaegwan.global.dto.CommonResponse;
import com.bwmanager.jaegwan.ingredient.dto.IngredientRequest;
import com.bwmanager.jaegwan.ingredient.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping
    public ResponseEntity<?> getIngredientInfo(@RequestBody IngredientRequest request) {
        CommonResponse<Object> response = CommonResponse.builder()
                .data(ingredientService.getIngredientsInfo(request.getRestaurantId()))
                .message("종류별 재료 현황 조회에 성공했습니다.")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIngredientInfo(@PathVariable("id") Long id) {
        CommonResponse<Object> response = CommonResponse.builder()
                .data(ingredientService.getIngredientDetailsInfo(id))
                .message("한 종류의 재료 상세 조회에 성공했습니다.")
                .build();

        return ResponseEntity.ok(response);
    }
}
