package com.bwmanager.jaegwan.receipt.service;

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
}
