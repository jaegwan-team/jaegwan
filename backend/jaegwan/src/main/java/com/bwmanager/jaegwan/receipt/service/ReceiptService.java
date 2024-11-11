package com.bwmanager.jaegwan.receipt.service;

import com.bwmanager.jaegwan.global.util.dto.OcrResponse;
import com.bwmanager.jaegwan.receipt.dto.ImageUrlRequest;
import com.bwmanager.jaegwan.receipt.dto.ReceiptDetailResponse;
import com.bwmanager.jaegwan.receipt.dto.ReceiptIngredientConfirmRequest;
import com.bwmanager.jaegwan.receipt.dto.ReceiptResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReceiptService {

    /**
     * 영수증 사진들을 올리고 저장한다.
     *
     * @param files
     * @return
     * @throws IOException
     */
    List<String> saveReceipt(Long restaurantId, List<MultipartFile> files) throws IOException;

    /**
     * 영수증 사진을 분석하여 재료를 저장한다.
     *
     * @param request
     * @return
     */
    List<OcrResponse> imageOcr(ImageUrlRequest request);

    /**
     * 영수증으로 등록된 구매 내역을 조회한다.
     *
     * @param restaurantId 식당 ID
     * @return 구매 내역 정보
     */
    List<ReceiptResponse> getReceiptsInfo(Long restaurantId);

    /**
     * 확정된 구매 내역 상세를 조회한다.
     *
     * @param receiptId 영수증 ID
     * @return 구매 내역 상세
     */
    List<ReceiptDetailResponse> getReceiptDetail(Long receiptId);

    /**
     * 구매 내역에 있는 재료를 확정한다.
     *
     * @param request 확정 요청에 필요한 DTO
     */
    void confirmReceiptIngredient(ReceiptIngredientConfirmRequest request);

    /**
     * 구매 내역에 있는 재료를 삭제한다.
     *
     * @param receiptIngredientId 영수증-재료 ID
     */
    void deleteReceiptIngredient(Long receiptIngredientId);

    /**
     * S3 서버에 올라간 이미지를 조회한다.
     *
     * @param receiptId
     * @return
     */
    String getReceiptImage(Long receiptId);
}
