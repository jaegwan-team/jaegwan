package com.bwmanager.jaegwan.ingredient.repository;

import com.bwmanager.jaegwan.ingredient.entity.IngredientDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientDetailRepository extends JpaRepository<IngredientDetail, Long>, IngredientDetailCustomRepository {

}
