package com.bwmanager.jaegwan.ingredient.repository;


import com.bwmanager.jaegwan.ingredient.dto.IngredientDetailResponse;
import com.bwmanager.jaegwan.ingredient.dto.IngredientResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.bwmanager.jaegwan.ingredient.entity.QIngredientDetail.ingredientDetail;

@RequiredArgsConstructor
@Repository
public class IngredientDetailCustomRepositoryImpl implements IngredientDetailCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public IngredientResponse getIngredientsInfo(Long ingredientId) {

        return jpaQueryFactory
                .select(Projections.fields(IngredientResponse.class,
                        ingredientDetail.ingredient.id,
                        ingredientDetail.ingredient.category,
                        ingredientDetail.amount.sum(),
                        ingredientDetail.ingredient.unit,
                        Expressions.numberTemplate(Long.class,
                                "DATEDIFF({0}, {1})",
                                ingredientDetail.expirationDate.min(), LocalDateTime.now()).as("leftExpirationDay")))
                .from(ingredientDetail)
                .where(ingredientDetail.ingredient.id.eq(ingredientId))
                .fetchOne();
    }

    @Override
    public List<IngredientDetailResponse> getIngredientDetailsInfoByIngredientId(Long ingredientId) {

        return jpaQueryFactory
                .select(Projections.fields(IngredientDetailResponse.class,
                        ingredientDetail.purchaseDate,
                        ingredientDetail.amount,
                        Expressions.numberTemplate(Long.class,
                                "DATEDIFF({0}, {1})",
                                ingredientDetail.expirationDate, LocalDateTime.now()).as("leftExpirationDay")))
                .from(ingredientDetail)
                .where(ingredientDetail.ingredient.id.eq(ingredientId))
                .fetch();
    }
}
