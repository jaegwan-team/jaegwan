package com.bwmanager.jaegwan.receipt.repository;

import com.bwmanager.jaegwan.receipt.dto.ReceiptResponse;

import java.util.List;
import java.util.Optional;

public interface ReceiptCustomRepository {

    /**
     * 구매 내역 정보를 조회한다.
     *
     * @param restaurantId 식당 ID
     * @return 구매 내역 정보
     */
    List<ReceiptResponse> getReceiptsInfoByRestaurantId(Long restaurantId);

    /**
     * S3 서버에 올라간 이미지를 조회한다.
     *
     * @param receiptId
     * @return
     */
    Optional<String> getImageUrlByReceiptId(Long receiptId);
}
