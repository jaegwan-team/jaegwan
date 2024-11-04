package com.bwmanager.jaegwan.ingredient.service;

import com.bwmanager.jaegwan.ingredient.dto.IngredientDetailResponse;
import com.bwmanager.jaegwan.ingredient.dto.IngredientResponse;

import java.util.List;

public interface IngredientService {

    /**
     * 종류별로 재료 정보를 조회한다.
     *
     * @param restaurantId 식당 ID
     * @return 종류별 재료 정보
     */
    List<IngredientResponse> getIngredientsInfo(Long restaurantId);

    /**
     * 한 종류에 해당하는 재료에 대한 상세 정보를 조회한다.
     *
     * @param id 재료 ID
     * @return 한 종류에 해당하는 재료 정보
     */
    List<IngredientDetailResponse> getIngredientDetailsInfo(Long id);
}
