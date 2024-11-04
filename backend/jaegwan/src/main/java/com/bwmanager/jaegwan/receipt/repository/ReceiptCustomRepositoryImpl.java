package com.bwmanager.jaegwan.receipt.repository;

import com.bwmanager.jaegwan.receipt.dto.QReceiptResponse;
import com.bwmanager.jaegwan.receipt.dto.ReceiptResponse;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bwmanager.jaegwan.ingredient.entity.QIngredient.ingredient;
import static com.bwmanager.jaegwan.receipt.entity.QReceipt.receipt;
import static com.bwmanager.jaegwan.receipt.entity.QReceiptIngredient.receiptIngredient;

@RequiredArgsConstructor
@Repository
public class ReceiptCustomRepositoryImpl implements ReceiptCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReceiptResponse> getReceiptsInfoByRestaurantId(Long restaurantId) {

        return jpaQueryFactory
                .select(new QReceiptResponse(
                        receipt.id,
                        JPAExpressions
                                .select(ingredient.name)
                                .from(receiptIngredient)
                                .where(receiptIngredient.receipt.id.eq(receipt.id))
                                .orderBy(receiptIngredient.amount.desc())
                                .limit(1),
                        JPAExpressions
                                .select(receiptIngredient.count().castToNum(Integer.class).subtract(1))
                                .from(receiptIngredient)
                                .where(receiptIngredient.receipt.id.eq(receipt.id)),
                        receipt.creationDate,
                        JPAExpressions
                                .select(Wildcard.count.eq(0L))
                                .from(receiptIngredient)
                                .where(receiptIngredient.receipt.id.eq(receipt.id),
                                        receiptIngredient.isConfirmed.eq(false))
                ))
                .from(receipt, receiptIngredient)
                .where(receipt.restaurant.id.eq(restaurantId),
                        receipt.id.eq(receiptIngredient.receipt.id))
                .fetch();
    }
}
