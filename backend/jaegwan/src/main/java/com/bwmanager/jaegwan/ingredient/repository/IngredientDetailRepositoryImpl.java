package com.bwmanager.jaegwan.ingredient.repository;

import com.bwmanager.jaegwan.ingredient.dto.IngredientDetailResponse;
import com.bwmanager.jaegwan.ingredient.dto.IngredientResponse;
import com.bwmanager.jaegwan.ingredient.dto.QIngredientDetailResponse;
import com.bwmanager.jaegwan.ingredient.dto.QIngredientResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.bwmanager.jaegwan.ingredient.entity.QIngredientDetail.ingredientDetail;
import static com.querydsl.core.types.dsl.Expressions.numberTemplate;

@RequiredArgsConstructor
@Repository
public class IngredientDetailRepositoryImpl implements IngredientDetailCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public IngredientResponse getIngredientInfo(Long ingredientId) {
        return jpaQueryFactory
                .select(new QIngredientResponse(
                        ingredientDetail.ingredient.id,
                        ingredientDetail.ingredient.category,
                        ingredientDetail.amount.sum(),
                        ingredientDetail.ingredient.unit,
                        numberTemplate(Integer.class,
                                "DATEDIFF({0}, {1})",
                                ingredientDetail.expirationDate.min(), LocalDateTime.now())))
                .from(ingredientDetail)
                .where(ingredientDetail.ingredient.id.eq(ingredientId))
                .fetchOne();
    }

    @Override
    public List<IngredientDetailResponse> getIngredientDetailsInfoByIngredientId(Long ingredientId) {
        return jpaQueryFactory
                .select(new QIngredientDetailResponse(
                        ingredientDetail.purchaseDate,
                        ingredientDetail.amount,
                        numberTemplate(Integer.class,
                                "DATEDIFF({0}, {1})",
                                ingredientDetail.expirationDate, LocalDateTime.now())))
                .from(ingredientDetail)
                .where(ingredientDetail.ingredient.id.eq(ingredientId))
                .fetch();
    }
}
