package com.bwmanager.jaegwan.ingredient.repository;

import com.bwmanager.jaegwan.ingredient.dto.IngredientAutoCompleteResponse;

public interface IngredientCustomRepository {

    IngredientAutoCompleteResponse getAutoCompleteResult(Long restaurantId, String word);
}
