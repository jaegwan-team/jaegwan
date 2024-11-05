package com.bwmanager.jaegwan.ingredient.repository;

import com.bwmanager.jaegwan.ingredient.entity.Ingredient;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>, IngredientCustomRepository {

    /**
     * 식당에 있는 모든 재료를 조회한다.
     *
     * @param restaurantId 식당 ID
     * @return 식당에 있는 재료 객체들
     */
    List<Ingredient> findAllByRestaurantId(Long restaurantId);

    Optional<Ingredient> findByRestaurantIdAndName(Long restaurantId, String name);

}
