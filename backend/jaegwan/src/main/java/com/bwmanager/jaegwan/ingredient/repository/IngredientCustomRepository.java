package com.bwmanager.jaegwan.ingredient.repository;

import com.bwmanager.jaegwan.ingredient.dto.IngredientAutoCompleteResponse;

import java.util.List;

public interface IngredientCustomRepository {

    List<IngredientAutoCompleteResponse> getAutoCompleteResult(Long restaurantId, String word);
}
