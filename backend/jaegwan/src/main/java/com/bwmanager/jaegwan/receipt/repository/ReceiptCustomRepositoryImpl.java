package com.bwmanager.jaegwan.receipt.repository;

import com.bwmanager.jaegwan.receipt.dto.QReceiptResponse;
import com.bwmanager.jaegwan.receipt.dto.ReceiptResponse;
import com.bwmanager.jaegwan.receipt.entity.QReceiptIngredient;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.bwmanager.jaegwan.receipt.entity.QReceipt.receipt;
import static com.bwmanager.jaegwan.receipt.entity.QReceiptIngredient.receiptIngredient;
import static com.bwmanager.jaegwan.restaurant.entity.QRestaurant.restaurant;
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
                        new CaseBuilder()
                                .when(receiptIngredient.count().ne(0L)).then(select(receiptIngredientSub.ingredient.name)
                                        .from(receiptIngredientSub)
                                        .where(receiptIngredientSub.id.eq(
                                                select(receiptIngredientSub.id.max())
                                                        .from(receiptIngredientSub)
                                                        .where(receiptIngredientSub.receipt.id.eq(receipt.id)))))
                                .otherwise(""),
                        new CaseBuilder()
                                .when(receiptIngredient.count().ne(0L))
                                .then(select(receiptIngredientSub.count().castToNum(Integer.class).subtract(1))
                                        .from(receiptIngredientSub)
                                        .where(receiptIngredientSub.receipt.id.eq(receipt.id)))
                                .otherwise(0),
                        receipt.createdDate,
                        new CaseBuilder()
                                .when(receiptIngredient.count().ne(0L)).then(select(receiptIngredientSub.count().eq(0L))
                                        .from(receiptIngredientSub)
                                        .where(receiptIngredientSub.receipt.id.eq(receipt.id),
                                                receiptIngredientSub.isConfirmed.eq(false)))
                                .otherwise(false)
                ))
                .from(receiptIngredient)
                .rightJoin(receiptIngredient.receipt, receipt)
                .join(receipt.restaurant, restaurant)
                .groupBy(receipt.id, receipt.createdDate)
                .fetch();
    }

    @Override
    public Optional<String> getImageUrlById(Long id) {
        String imageUrl = jpaQueryFactory
                .select(receipt.imageUrl)
                .from(receipt)
                .where(receipt.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(imageUrl);
    }
}
