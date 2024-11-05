package com.bwmanager.jaegwan.receipt.service;

import com.bwmanager.jaegwan.global.error.ErrorCode;
import com.bwmanager.jaegwan.global.error.exception.RestaurantException;
import com.bwmanager.jaegwan.global.util.S3Util;
import com.bwmanager.jaegwan.receipt.dto.ReceiptResponse;
import com.bwmanager.jaegwan.receipt.entity.Receipt;
import com.bwmanager.jaegwan.receipt.repository.ReceiptRepository;
import com.bwmanager.jaegwan.restaurant.entity.Restaurant;
import com.bwmanager.jaegwan.restaurant.repository.RestaurantRepository;
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
    private final RestaurantRepository restaurantRepository;
    private final String prefix = "receipt";

    @Override
    public List<String> saveReceipt(Long restaurantId, List<MultipartFile> files) throws IOException {
        List<String> receiptUrls = new ArrayList<>();
        log.info("saveReceipt() restaurant id: " + restaurantId);

        for (MultipartFile file : files) {
            String imageUrl = s3Util.upload(file, prefix, restaurantId);
            receiptUrls.add(imageUrl);

            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new RestaurantException(ErrorCode.RESTAURANT_NOT_FOUND));

            Receipt receipt = Receipt.builder()
                    .imageUrl(imageUrl)
                    .restaurant(restaurant)
                    .build();

            receiptRepository.save(receipt);
        }

        return receiptUrls;
    }

    @Override
    public List<ReceiptResponse> getReceiptsInfo(Long restaurantId) {
        log.info("size={}", receiptRepository.getReceiptsInfoByRestaurantId(restaurantId).size());
        return receiptRepository.getReceiptsInfoByRestaurantId(restaurantId);
    }
}
