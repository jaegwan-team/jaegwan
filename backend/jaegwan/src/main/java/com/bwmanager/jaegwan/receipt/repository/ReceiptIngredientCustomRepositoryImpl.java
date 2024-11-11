package com.bwmanager.jaegwan.receipt.repository;

import com.bwmanager.jaegwan.receipt.dto.QReceiptDetailResponse;
import com.bwmanager.jaegwan.receipt.dto.ReceiptDetailResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bwmanager.jaegwan.receipt.entity.QReceipt.receipt;
import static com.bwmanager.jaegwan.receipt.entity.QReceiptIngredient.receiptIngredient;

@RequiredArgsConstructor
@Repository
public class ReceiptIngredientCustomRepositoryImpl implements ReceiptIngredientCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReceiptDetailResponse> getReceiptsDetailByReceiptId(Long receiptId) {
        return jpaQueryFactory
                .select(new QReceiptDetailResponse(
                        receiptIngredient.ingredient.name,
                        receiptIngredient.amount,
                        receiptIngredient.ingredient.unit
                ))
                .from(receiptIngredient)
                .join(receiptIngredient.receipt, receipt)
                .fetch();
    }
}
