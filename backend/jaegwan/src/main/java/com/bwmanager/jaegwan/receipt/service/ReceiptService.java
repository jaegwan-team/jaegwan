package com.bwmanager.jaegwan.receipt.service;

import com.bwmanager.jaegwan.receipt.dto.ReceiptResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReceiptService {

    /**
     * 영수증 사진을 올리고 저장한다.
     *
     * @param files
     * @return
     * @throws IOException
     */
    List<String> saveReceipt(Long restaurantId, List<MultipartFile> files) throws IOException;

    /**
     * 영수증으로 등록된 구매 내역을 조회한다.
     *
     * @param restaurantId 식당 ID
     * @return 구매 내역 정보
     */
    List<ReceiptResponse> getReceiptsInfo(Long restaurantId);
}
