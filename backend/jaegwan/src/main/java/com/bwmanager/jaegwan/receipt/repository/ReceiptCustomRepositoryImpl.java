package com.bwmanager.jaegwan.receipt.repository;

import com.bwmanager.jaegwan.receipt.dto.QReceiptResponse;
import com.bwmanager.jaegwan.receipt.dto.ReceiptResponse;
import com.bwmanager.jaegwan.receipt.entity.QReceiptIngredient;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
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

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ReceiptResponse> getReceiptsInfoByRestaurantId(Long restaurantId, boolean isAll) {

        QReceiptIngredient receiptIngredientSub = new QReceiptIngredient("receiptIngredientSub");

        SimpleExpression<Boolean> isConfirmed = new CaseBuilder()
                .when(receiptIngredient.count().ne(0L)).then(select(receiptIngredientSub.count().eq(0L))
                        .from(receiptIngredientSub)
                        .where(receiptIngredientSub.receipt.id.eq(receipt.id),
                                receiptIngredientSub.isConfirmed.eq(false)))
                .otherwise(false);

        NumberExpression<Integer> orderConfirmed = new CaseBuilder()
                .when(isConfirmed.eq(false)).then(0)
                .otherwise(1);

        return queryFactory
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
                        isConfirmed
                ))
                .from(receiptIngredient)
                .rightJoin(receiptIngredient.receipt, receipt)
                .join(receipt.restaurant, restaurant)
                .where(selectConfirmed(isAll),
                        restaurant.id.eq(restaurantId))
                .groupBy(receipt.id, receipt.createdDate)
                .orderBy(orderConfirmed.asc(),
                        receipt.createdDate.desc())
                .fetch();
    }

    //전체를 불러올지, 확정되지 않은 구매내역만 불러올지를 결정한다.
    private BooleanExpression selectConfirmed(Boolean isAll) {
        return isAll? null: select(receiptIngredient.count())
                .from(receiptIngredient)
                .where(receiptIngredient.receipt.id.eq(receipt.id),
                        receiptIngredient.isConfirmed.eq(true))
                .eq(0L);
    }

    @Override
    public Optional<String> getImageUrlById(Long id) {
        String imageUrl = queryFactory
                .select(receipt.imageUrl)
                .from(receipt)
                .where(receipt.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(imageUrl);
    }
}
