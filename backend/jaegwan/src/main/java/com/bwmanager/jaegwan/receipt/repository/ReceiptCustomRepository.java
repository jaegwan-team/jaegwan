package com.bwmanager.jaegwan.receipt.repository;

import com.bwmanager.jaegwan.receipt.dto.ReceiptResponse;

import java.util.List;
import java.util.Optional;

public interface ReceiptCustomRepository {

    /**
     * 구매 내역 정보를 조회한다.
     *
     * @param restaurantId 식당 ID
     * @param isAll true: 모든 구매 내역 조회, false: 확정되지 않은 구매 내역 조회
     * @return 구매 내역 정보
     */
    List<ReceiptResponse> getReceiptsInfoByRestaurantId(Long restaurantId, boolean isAll);

    /**
     * S3 서버에 올라간 이미지를 조회한다.
     *
     * @param id
     * @return
     */
    Optional<String> getImageUrlById(Long id);
}
