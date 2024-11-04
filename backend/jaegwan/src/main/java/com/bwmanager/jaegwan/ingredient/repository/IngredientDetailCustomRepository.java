package com.bwmanager.jaegwan.ingredient.repository;

import com.bwmanager.jaegwan.ingredient.dto.IngredientDetailResponse;
import com.bwmanager.jaegwan.ingredient.dto.IngredientResponse;

import java.util.List;

public interface IngredientDetailCustomRepository {

    IngredientResponse getIngredientsInfo(Long ingredientId);

    List<IngredientDetailResponse> getIngredientDetailsInfoByIngredientId(Long ingredientId);
}
