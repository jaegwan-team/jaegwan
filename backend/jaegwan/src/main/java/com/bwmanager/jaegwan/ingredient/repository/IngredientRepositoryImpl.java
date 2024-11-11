package com.bwmanager.jaegwan.ingredient.repository;

import com.bwmanager.jaegwan.ingredient.dto.IngredientAutoCompleteResponse;
import com.bwmanager.jaegwan.ingredient.dto.QIngredientAutoCompleteResponse;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bwmanager.jaegwan.ingredient.entity.QIngredient.ingredient;

@RequiredArgsConstructor
@Repository
public class IngredientRepositoryImpl implements IngredientCustomRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<IngredientAutoCompleteResponse> getAutoCompleteResult(Long restaurantId, String word) {
        NumberExpression<Integer> nameRank = new CaseBuilder()
                .when(ingredient.name.eq(word)).then(1)
                .when(ingredient.name.startsWith(word)).then(2)
                .when(ingredient.name.like("%" + word)).then(3)
                .otherwise(4);

        return queryFactory
                .select(new QIngredientAutoCompleteResponse(ingredient.name, ingredient.category))
                .from(ingredient)
                .where(ingredient.restaurant.id.eq(restaurantId),
                        ingredient.name.contains(word))
                .orderBy(nameRank.asc())
                .fetch();


    }
}
