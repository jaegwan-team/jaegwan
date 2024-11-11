package com.bwmanager.jaegwan.receipt.repository;

import com.bwmanager.jaegwan.receipt.dto.QReceiptResponse;
import com.bwmanager.jaegwan.receipt.dto.ReceiptResponse;
import com.bwmanager.jaegwan.receipt.entity.QReceiptIngredient;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bwmanager.jaegwan.receipt.entity.QReceipt.receipt;
import static com.bwmanager.jaegwan.receipt.entity.QReceiptIngredient.receiptIngredient;
import static com.querydsl.jpa.JPAExpressions.select;

@RequiredArgsConstructor
@Repository
public class ReceiptCustomRepositoryImpl implements ReceiptCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReceiptResponse> getReceiptsInfoByRestaurantId(Long restaurantId) {
        QReceiptIngredient receiptIngredientSub = new QReceiptIngredient("receiptIngredientSub");

        return jpaQueryFactory
                .select(new QReceiptResponse(
                        receipt.id,
                        select(receiptIngredientSub.ingredient.name)
                                .from(receiptIngredientSub)
                                .where(receiptIngredientSub.id.eq(receiptIngredient.id))
                                .limit(1),
                        select(receiptIngredientSub.count().castToNum(Integer.class).subtract(1))
                                .from(receiptIngredientSub)
                                .where(receiptIngredientSub.id.eq(receiptIngredient.id)),
                        receipt.createdDate,
                        select(receiptIngredientSub.count().eq(0L))
                                .from(receiptIngredientSub)
                                .where(receiptIngredientSub.id.eq(receiptIngredient.id),
                                        receiptIngredientSub.isConfirmed.eq(false))
                ))
                .from(receiptIngredient)
                .join(receiptIngredient.receipt, receipt)
                .where(receipt.restaurant.id.eq(restaurantId))
                .fetch();
    }
}
