package com.bwmanager.jaegwan.receipt.service;

import com.bwmanager.jaegwan.global.converter.EnumValueConvertUtils;
import com.bwmanager.jaegwan.global.error.ErrorCode;
import com.bwmanager.jaegwan.global.error.exception.NotFoundException;
import com.bwmanager.jaegwan.global.error.exception.RestaurantException;
import com.bwmanager.jaegwan.global.util.OcrFeignClient;
import com.bwmanager.jaegwan.global.util.S3Util;
import com.bwmanager.jaegwan.global.util.dto.OcrRequest;
import com.bwmanager.jaegwan.global.util.dto.OcrResponse;
import com.bwmanager.jaegwan.ingredient.entity.Category;
import com.bwmanager.jaegwan.ingredient.entity.Ingredient;
import com.bwmanager.jaegwan.ingredient.entity.IngredientDetail;
import com.bwmanager.jaegwan.ingredient.entity.Unit;
import com.bwmanager.jaegwan.ingredient.exception.IngredientServiceException;
import com.bwmanager.jaegwan.ingredient.repository.IngredientDetailRepository;
import com.bwmanager.jaegwan.ingredient.repository.IngredientRepository;
import com.bwmanager.jaegwan.receipt.dto.*;
import com.bwmanager.jaegwan.receipt.entity.Receipt;
import com.bwmanager.jaegwan.receipt.entity.ReceiptIngredient;
import com.bwmanager.jaegwan.receipt.repository.ReceiptIngredientRepository;
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
    private final OcrFeignClient ocrFeignClient;
    private final ReceiptRepository receiptRepository;
    private final RestaurantRepository restaurantRepository;
    private final IngredientRepository ingredientRepository;
    private final IngredientDetailRepository ingredientDetailRepository;
    private final ReceiptIngredientRepository receiptIngredientRepository;
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
    public List<OcrResponse> imageOcr(ImageUrlRequest request) {
        List<OcrResponse> ocrResponses = ocrFeignClient.imageOcr(OcrRequest.builder().image_url(request.getImageUrl()).build());

        for (OcrResponse ocrResponse : ocrResponses) {
            // 영수증-재료 저장
            ReceiptIngredient saved = receiptIngredientRepository.save(ReceiptIngredient.builder()
                    .amount(ocrResponse.getAmount())
                    .name(ocrResponse.getName())
                    .price(ocrResponse.getPrice())
                    .isConfirmed(false)
                    .receipt(receiptRepository.findByImageUrl(request.getImageUrl())
                            .orElseThrow(() -> new NotFoundException(ErrorCode.RECEIPT_NOT_FOUND)))
                    .build());
            log.info("receipt-ingredient -> {}", saved.getId());
        }

        return ocrResponses;
    }

    @Override
    public List<ReceiptResponse> getReceiptsInfo(Long restaurantId, boolean isAll) {
        List<ReceiptResponse> receiptResponses = receiptRepository.getReceiptsInfoByRestaurantId(restaurantId, isAll);
        log.info("size={}", receiptResponses.size());
        return receiptResponses;
    }

    @Override
    public List<ReceiptDetailResponse> getReceiptDetail(Long receiptId) {
        List<ReceiptDetailResponse> receiptDetailResponses = receiptIngredientRepository.getReceiptsDetailByReceiptId(receiptId);
        log.info("size={}", receiptDetailResponses.size());
        return receiptDetailResponses;
    }

    @Override
    public void confirmReceiptIngredient(ReceiptIngredientConfirmRequest request) {
        //영수증과 식당 조회
        Receipt receipt = receiptRepository.findById(request.getReceiptId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.RECEIPT_NOT_FOUND));
        Restaurant restaurant = receipt.getRestaurant();

        request.getReceiptIngredientConfirmData().forEach(data -> confirmReceipt(data, receipt, restaurant));
    }

    private void confirmReceipt(ReceiptIngredientConfirmData data, Receipt receipt, Restaurant restaurant) {
        // 추가로 영수증-재료에 등록됐다면 데이터 저장
        ReceiptIngredient receiptIngredient = receiptIngredientRepository.findById(data.getReceiptIngredientId())
                .orElseGet(() -> receiptIngredientRepository.save(ReceiptIngredient.builder()
                        .amount(data.getAmount())
                        .price(data.getPrice())
                        .expirationDate(data.getExpirationDate())
                        .isConfirmed(true)
                        .receipt(receipt)
                        .build()));

        // 1. 재료 이름에 해당하는 재료가 존재하는지 여부 조회
        String ingredientName = data.getName();
        ingredientRepository
                .findByRestaurantIdAndName(restaurant.getId(), ingredientName)
                .ifPresentOrElse(
                        // 1-1. 존재하면 해당 재료를 연관 관계에 추가
                        ingredient -> receiptIngredient.confirm(ingredient, data.getAmount(), data.getPrice(), data.getExpirationDate()),
                        // 1-2. 존재하지 않으면 재료이름으로 재료를 추가하고 연관 관계에 추가
                        () -> {
                            Ingredient savedIngredient = ingredientRepository.save(Ingredient.builder()
                                    .name(data.getName())
                                    .category(EnumValueConvertUtils.ofDesc(Category.class, ErrorCode.INGREDIENT_CATEGORY_NOT_FOUND, data.getCategory()))
                                    .unit(EnumValueConvertUtils.ofDesc(Unit.class, ErrorCode.INGREDIENT_UNIT_NOT_FOUND, data.getUnit()))
                                    .restaurant(restaurant)
                                    .build());
                            log.info("재료가 추가됨 - {}", savedIngredient.getName());
                            receiptIngredient.confirm(savedIngredient, data.getAmount(), data.getPrice(), data.getExpirationDate());
                        }
                );

        // 2. 재료 잔고에 반영
        Ingredient ingredient = ingredientRepository
                .findByRestaurantIdAndName(restaurant.getId(), ingredientName)
                .orElseThrow(() -> new IngredientServiceException(ErrorCode.INGREDIENT_NOT_FOUND));

        ingredientDetailRepository.save(IngredientDetail.builder()
                .ingredient(ingredient)
                .amount(data.getAmount())
                .purchaseDate(receiptIngredient.getReceipt().getCreatedDate())
                .expirationDate(data.getExpirationDate().atStartOfDay())
                .build());

        // 3. 삭제한 구매 내역 재료 삭제
       receiptIngredientRepository.deleteAllByIsConfirmedFalseAndReceiptId(receipt.getId());
    }

    @Override
    public void deleteReceiptIngredient(Long receiptIngredientId) {
        receiptIngredientRepository.deleteById(receiptIngredientId);
    }

    @Transactional(readOnly = true)
    @Override
    public String getReceiptImage(Long id) {
        return receiptRepository.getImageUrlById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.IMAGE_NOT_FOUND));
    }
}
