package com.bwmanager.jaegwan.ingredient.repository;

import com.bwmanager.jaegwan.ingredient.dto.IngredientDetailResponse;
import com.bwmanager.jaegwan.ingredient.dto.IngredientResponse;
import com.bwmanager.jaegwan.ingredient.dto.QIngredientDetailResponse;
import com.bwmanager.jaegwan.ingredient.dto.QIngredientResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bwmanager.jaegwan.ingredient.entity.QIngredientDetail.ingredientDetail;

@RequiredArgsConstructor
@Repository
public class IngredientDetailRepositoryImpl implements IngredientDetailCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public IngredientResponse getIngredientInfo(Long ingredientId) {
        return queryFactory
                .select(new QIngredientResponse(
                        ingredientDetail.ingredient.id,
                        ingredientDetail.ingredient.name,
                        ingredientDetail.ingredient.category,
                        ingredientDetail.amount.sum(),
                        ingredientDetail.ingredient.unit,
                        ingredientDetail.expirationDate.min()))
                .from(ingredientDetail)
                .where(ingredientDetail.ingredient.id.eq(ingredientId))
                .fetchOne();
    }

    @Override
    public List<IngredientDetailResponse> getIngredientDetailsInfoByIngredientId(Long ingredientId) {
        return queryFactory
                .select(new QIngredientDetailResponse(
                        ingredientDetail.purchaseDate,
                        ingredientDetail.amount,
                        ingredientDetail.expirationDate))
                .from(ingredientDetail)
                .where(ingredientDetail.ingredient.id.eq(ingredientId))
                .fetch();
    }
}
