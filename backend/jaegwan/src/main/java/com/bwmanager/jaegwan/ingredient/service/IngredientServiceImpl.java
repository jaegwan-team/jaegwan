package com.bwmanager.jaegwan.ingredient.service;

import com.bwmanager.jaegwan.ingredient.dto.IngredientDetailResponse;
import com.bwmanager.jaegwan.ingredient.dto.IngredientResponse;
import com.bwmanager.jaegwan.ingredient.entity.Ingredient;
import com.bwmanager.jaegwan.ingredient.repository.IngredientDetailRepository;
import com.bwmanager.jaegwan.ingredient.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientDetailRepository ingredientDetailRepository;

    @Override
    public List<IngredientResponse> getIngredientsInfo(Long restaurantId) {
        // STEP 1. 식당에 존재하는 모든 재료를 조회
        List<Ingredient> ingredients = ingredientRepository.findAllByRestaurantId(restaurantId);
        log.info("식당 재료 개수: {}", ingredients.size());
        // STEP 2. 종류별 재료 잔여량 및 가장 짧은 유통기한일 조회
        return ingredients.stream()
                .map(ingredient -> ingredientDetailRepository.getIngredientInfo(ingredient.getId()))
                .toList();
    }

    @Override
    public List<IngredientDetailResponse> getIngredientDetailsInfo(Long id) {
        // STEP 1. 재료 종류 ID를 통해 모든 재료 현황 상세 조회
        return ingredientDetailRepository.getIngredientDetailsInfoByIngredientId(id);
    }

    @Override
    public void deleteIngredientDetail(Long ingredientDetailId) {
        ingredientDetailRepository.deleteById(ingredientDetailId);
    }
}
