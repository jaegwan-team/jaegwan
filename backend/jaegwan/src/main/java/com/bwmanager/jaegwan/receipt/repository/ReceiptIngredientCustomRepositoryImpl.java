package com.bwmanager.jaegwan.receipt.repository;

import com.bwmanager.jaegwan.receipt.dto.QReceiptDetailResponse;
import com.bwmanager.jaegwan.receipt.dto.ReceiptDetailResponse;
import com.bwmanager.jaegwan.receipt.entity.QReceiptIngredient;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bwmanager.jaegwan.receipt.entity.QReceiptIngredient.receiptIngredient;
import static com.querydsl.jpa.JPAExpressions.select;

@RequiredArgsConstructor
@Repository
public class ReceiptIngredientCustomRepositoryImpl implements ReceiptIngredientCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ReceiptDetailResponse> getReceiptsDetailByReceiptId(Long receiptId) {

        QReceiptIngredient receiptIngredientSub = new QReceiptIngredient("receiptIngredientSub");

        JPQLQuery<String> categoryIfExist = select(new CaseBuilder()
                .when(receiptIngredientSub.ingredient.isNotNull()).then(receiptIngredientSub.ingredient.category.toString())
                .otherwise(""))
                .from(receiptIngredientSub)
                .where(receiptIngredient.id.eq(receiptIngredientSub.id));

        JPQLQuery<String> unitIfExist = select(new CaseBuilder()
                .when(receiptIngredientSub.ingredient.isNotNull()).then(receiptIngredientSub.ingredient.unit.toString())
                .otherwise(""))
                .from(receiptIngredientSub)
                .where(receiptIngredient.id.eq(receiptIngredientSub.id));

        return queryFactory
                .select(new QReceiptDetailResponse(
                        receiptIngredient.id,
                        receiptIngredient.name,
                        categoryIfExist,
                        receiptIngredient.price,
                        receiptIngredient.amount,
                        unitIfExist,
                        receiptIngredient.expirationDate
                ))
                .from(receiptIngredient)
                .where(receiptIngredient.receipt.id.eq(receiptId))
                .fetch();
    }
}
