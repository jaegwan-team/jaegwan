package com.bwmanager.jaegwan.receipt.repository;

import com.bwmanager.jaegwan.receipt.dto.QReceiptResponse;
import com.bwmanager.jaegwan.receipt.dto.ReceiptResponse;
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

        return jpaQueryFactory
                .select(new QReceiptResponse(
                        receipt.id,
                        select(receiptIngredient.ingredient.name)
                                .from(receiptIngredient)
                                .where(receiptIngredient.id.eq(
                                        select(receiptIngredient.id.max())
                                                .from(receiptIngredient)
                                                .where(receiptIngredient.receipt.id.eq(receipt.id)))),
                        select(receiptIngredient.count().castToNum(Integer.class).subtract(1))
                                .from(receiptIngredient)
                                .where(receiptIngredient.receipt.id.eq(receipt.id)),
                        receipt.createdDate,
                        select(receiptIngredient.count().eq(0L))
                                .from(receiptIngredient)
                                .where(receiptIngredient.receipt.id.eq(receipt.id),
                                        receiptIngredient.isConfirmed.eq(false))
                ))
                .from(receipt)
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
