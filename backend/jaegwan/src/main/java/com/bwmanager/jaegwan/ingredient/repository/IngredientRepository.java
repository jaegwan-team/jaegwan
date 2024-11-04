package com.bwmanager.jaegwan.ingredient.repository;

import com.bwmanager.jaegwan.ingredient.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>, IngredientCustomRepository {

    /**
     * 식당에 있는 모든 재료를 조회한다.
     *
     * @param restaurantId 식당 ID
     * @return 식당에 있는 재료 객체들
     */
    List<Ingredient> findAllByRestaurantId(Long restaurantId);
}
