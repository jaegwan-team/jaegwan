package com.bwmanager.jaegwan.ingredient.repository;

import com.bwmanager.jaegwan.ingredient.entity.IngredientDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredientDetailRepository extends JpaRepository<IngredientDetail, Long>, IngredientDetailCustomRepository {

}
