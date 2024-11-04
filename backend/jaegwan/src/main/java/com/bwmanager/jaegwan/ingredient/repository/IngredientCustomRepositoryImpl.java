package com.bwmanager.jaegwan.ingredient.repository;

import com.bwmanager.jaegwan.ingredient.entity.Ingredient;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class IngredientCustomRepositoryImpl implements IngredientCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

}
