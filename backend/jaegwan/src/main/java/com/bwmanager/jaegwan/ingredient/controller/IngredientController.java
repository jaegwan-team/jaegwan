package com.bwmanager.jaegwan.ingredient.controller;

import com.bwmanager.jaegwan.global.dto.CommonResponse;
import com.bwmanager.jaegwan.ingredient.dto.IngredientRequest;
import com.bwmanager.jaegwan.ingredient.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    @Operation(summary = "등록된 재료 현황 조회", description = "id(restaurantId)가 필요합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록된 재료 현황 조회에 성공했습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 데이터입니다.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 내부 에러가 발생했습니다.",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> getIngredientInfo(@RequestBody IngredientRequest request) {
        CommonResponse<Object> response = CommonResponse.builder()
                .data(ingredientService.getIngredientsInfo(request.getRestaurantId()))
                .message("종류별 재료 현황 조회에 성공했습니다.")
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "등록된 재료 상세 조회", description = "id(ingredientId)가 필요합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록된 재료 상세 조회에 성공했습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 데이터입니다.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 내부 에러가 발생했습니다.",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getIngredientInfo(@Parameter(description = "조회할 재료 ID", required = true, example = "1")
                                               @PathVariable("id") Long id) {
        CommonResponse<Object> response = CommonResponse.builder()
                .data(ingredientService.getIngredientDetailsInfo(id))
                .message("한 종류의 재료 상세 조회에 성공했습니다.")
                .build();

        return ResponseEntity.ok(response);
    }
}
