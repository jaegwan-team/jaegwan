package com.bwmanager.jaegwan.ingredient.repository;

import com.bwmanager.jaegwan.ingredient.entity.Ingredient;
import com.bwmanager.jaegwan.ingredient.entity.IngredientDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IngredientDetailRepository extends JpaRepository<IngredientDetail, Long> {

    @Query("SELECT SUM(i.amount) " +
            "FROM IngredientDetail i " +
            "WHERE i.ingredient.id = :ingredientId")
    double findTotalAmountByIngredientId(Long ingredientId);

    @Query("SELECT DATE_DIFF(CURRENT_DATE, i.expirationDate)  " +
            "FROM IngredientDetail i " +
            "WHERE i.id = :id")
    int findMinExpirationDayById(Long id);
}
