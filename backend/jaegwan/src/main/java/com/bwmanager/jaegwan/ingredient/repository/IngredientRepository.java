package com.bwmanager.jaegwan.ingredient.repository;

import com.bwmanager.jaegwan.ingredient.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAllByRestaurantId(Long restaurantId);
}
