package com.bwmanager.jaegwan.receipt.service;

import com.bwmanager.jaegwan.global.util.S3Util;
import com.bwmanager.jaegwan.receipt.entity.Receipt;
import com.bwmanager.jaegwan.receipt.repository.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final S3Util s3Util;
    private final ReceiptRepository receiptRepository;
    private final String prefix = "receipt";

    @Override
    public List<String> saveReceipt(Long restaurantId, List<MultipartFile> files) throws IOException {
        List<String> receiptUrls = new ArrayList<>();
        log.info("saveReceipt() restaurant id: " + restaurantId);

        for (MultipartFile file : files) {
            String imageUrl = s3Util.upload(file, prefix, restaurantId);
            receiptUrls.add(imageUrl);

            // TODO: restaurant 저장

            Receipt receipt = Receipt.builder()
                    .imageUrl(imageUrl)
                    .build();
            receiptRepository.save(receipt);
        }

        return receiptUrls;
    }


}
