package com.bwmanager.jaegwan.ingredient.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@RequiredArgsConstructor
@Repository
public class IngredientCustomRepositoryImpl implements IngredientCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

}
