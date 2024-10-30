package com.bwmanager.jaegwan.ingredient.service;

import com.bwmanager.jaegwan.ingredient.dto.IngredientResponse;
import com.bwmanager.jaegwan.ingredient.entity.Ingredient;
import com.bwmanager.jaegwan.ingredient.repository.IngredientDetailRepository;
import com.bwmanager.jaegwan.ingredient.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientDetailRepository ingredientDetailRepository;

    @Override
    public List<IngredientResponse> getIngredientsInfo(Long restaurantId) {
        // STEP 1. 식당에 존재하는 모든 재료를 조회
        List<Ingredient> ingredients = ingredientRepository.findAllByRestaurantId(restaurantId);

        // STEP 2. 종류별 재료 잔여량 및 가장 짧은 유통기한일 조회
        return ingredients.stream().map(ingredient -> IngredientResponse.builder()
                .id(ingredient.getId())
                .category(ingredient.getCategory())
                .totalAmount(ingredientDetailRepository.findTotalAmountByIngredientId(ingredient.getId()))
                .unit(ingredient.getUnit())
                .leftExpirationDay(ingredientDetailRepository.findMinExpirationDayById(ingredient.getId()))
                .build()).toList();
    }
}
