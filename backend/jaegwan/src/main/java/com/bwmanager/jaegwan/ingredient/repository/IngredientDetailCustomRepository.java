package com.bwmanager.jaegwan.ingredient.repository;

import com.bwmanager.jaegwan.ingredient.dto.IngredientDetailResponse;
import com.bwmanager.jaegwan.ingredient.dto.IngredientResponse;

import java.util.List;

public interface IngredientDetailCustomRepository {

    /**
     * 선택한 재료에 해당하는 간단한 정보를 조회한다. (재료 목록 화면에서 필요)
     *
     * @param ingredientId 조회할 재료 종류 ID
     * @return 재료 정보
     */
    IngredientResponse getIngredientInfo(Long ingredientId);

    /**
     * 선택한 재료에 헤당하는 상세 정보를 조회한다. (재료 목록 화면에서 재료 클릭 시 필요)
     *
     * @param ingredientId 조회할 재료 종류 ID
     * @return 재료 종류에 해당하는 재료 상세 정보들
     */
    List<IngredientDetailResponse> getIngredientDetailsInfoByIngredientId(Long ingredientId);
}
