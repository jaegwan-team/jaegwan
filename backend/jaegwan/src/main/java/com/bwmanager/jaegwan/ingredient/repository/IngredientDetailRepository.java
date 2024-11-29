package com.bwmanager.jaegwan.ingredient.repository;

import com.bwmanager.jaegwan.ingredient.entity.IngredientDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientDetailRepository extends JpaRepository<IngredientDetail, Long>, IngredientDetailCustomRepository {

    List<IngredientDetail> findAllByIngredientId(Long ingredientId);

    List<IngredientDetail> findAllByIngredientIdAndIngredient_Restaurant_id(Long ingredientId, Long restaurantId);

}
