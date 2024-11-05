package com.bwmanager.jaegwan.receipt.repository;

import com.bwmanager.jaegwan.receipt.dto.ReceiptDetailResponse;

import java.util.List;

public interface ReceiptIngredientCustomRepository {

    /**
     * 확정된 구매 상세 내역을 조회한다.
     *
     * @param receiptId 조회할 영수증 ID
     * @return 구매 상세 내역 정보(재료 이름, 양, 단위)
     */
    List<ReceiptDetailResponse> getReceiptsDetailByReceiptId(Long receiptId);
}
