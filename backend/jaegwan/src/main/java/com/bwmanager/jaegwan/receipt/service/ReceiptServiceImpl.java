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
import com.bwmanager.jaegwan.receipt.dto.ImageUrlRequest;
import com.bwmanager.jaegwan.receipt.dto.ReceiptDetailResponse;
import com.bwmanager.jaegwan.receipt.dto.ReceiptIngredientConfirmRequest;
import com.bwmanager.jaegwan.receipt.dto.ReceiptResponse;
import com.bwmanager.jaegwan.receipt.entity.Receipt;
import com.bwmanager.jaegwan.receipt.entity.ReceiptIngredient;
import com.bwmanager.jaegwan.receipt.exception.ReceiptIngredientNotFoundException;
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
//            // Category를 코드 기반으로 변환
//            Category category = EnumValueConvertUtils.ofCode(
//                    Category.class,
//                    ErrorCode.INGREDIENT_CATEGORY_NOT_FOUND,
//                    String.valueOf(ocrResponse.getCategory())
//            );
//            log.info("category -> {}", category.getDesc());
//
//            // name과 category로 재료 찾기
//            Ingredient ingredient = ingredientRepository.findByNameAndCategory(ocrResponse.getName(), category)
//                    .orElseGet(() -> {
//                        // 재료가 없으면 새로 생성하여 저장
//                        Ingredient newIngredient = Ingredient.builder()
//                                .name(ocrResponse.getName())
//                                .category(category)
//                                .build();
//                        return ingredientRepository.save(newIngredient);
//                    });
//            log.info("ingredient -> {}", ingredient.getName());
//
//           // 영수증-재료 저장
            ReceiptIngredient saved = receiptIngredientRepository.save(ReceiptIngredient.builder()
//                    .ingredient(ingredient)
                    .amount(ocrResponse.getAmount())
                    .isConfirmed(false)
                    .receipt(receiptRepository.findByImageUrl(request.getImageUrl())
                            .orElseThrow(() -> new NotFoundException(ErrorCode.RECEIPT_NOT_FOUND)))
                    .build());
            log.info("receipt-ingredient -> {}", saved.getId());
        }

        return ocrResponses;
    }

    @Override
    public List<ReceiptResponse> getReceiptsInfo(Long restaurantId) {
        List<ReceiptResponse> receiptResponses = receiptRepository.getReceiptsInfoByRestaurantId(restaurantId);
        log.info("size={}", receiptResponses.size());
        return receiptResponses;
    }

    @Override
    public List<ReceiptDetailResponse> getReceiptDetail(Long receiptId) {
        return receiptIngredientRepository.getReceiptsDetailByReceiptId(receiptId);
    }

    @Override
    public void confirmReceiptIngredient(ReceiptIngredientConfirmRequest request) {

        ReceiptIngredient receiptIngredient = receiptIngredientRepository.findById(request.getReceiptIngredientId())
                .orElseThrow(() -> new ReceiptIngredientNotFoundException(ErrorCode.RECEIPT_NOT_FOUND));
        Restaurant restaurant = receiptIngredient.getReceipt().getRestaurant();

        // 1. 재료 이름에 해당하는 재료가 존재하는지 여부 조회
        String ingredientName = request.getName();
        ingredientRepository
                .findByRestaurantIdAndName(restaurant.getId(), ingredientName)
                .ifPresentOrElse(
                        // 1-1. 존재하면 해당 재료를 연관 관계에 추가
                        receiptIngredient::confirm,
                        // 1-2. 존재하지 않으면 재료이름으로 재료를 추가하고 연관 관계에 추가
                        () -> {
                            Ingredient savedIngredient = ingredientRepository.save(Ingredient.builder()
                                    .name(request.getName())
                                    .category(EnumValueConvertUtils.ofDesc(Category.class, ErrorCode.INGREDIENT_CATEGORY_NOT_FOUND, request.getCategory()))
                                    .unit(EnumValueConvertUtils.ofDesc(Unit.class, ErrorCode.INGREDIENT_UNIT_NOT_FOUND, request.getUnit()))
                                    .restaurant(restaurant)
                                    .build());
                            log.info("재료가 추가됨 - {}", savedIngredient.getName());
                            receiptIngredient.confirm(savedIngredient);
                        }
                );

        // 2. 재료 잔고에 반영
        Ingredient ingredient = ingredientRepository
                .findByRestaurantIdAndName(restaurant.getId(), ingredientName)
                .orElseThrow(() -> new IngredientServiceException(ErrorCode.INGREDIENT_NOT_FOUND));

        ingredientDetailRepository.save(IngredientDetail.builder()
                .ingredient(ingredient)
                .amount(request.getAmount())
                .purchaseDate(receiptIngredient.getReceipt().getCreatedDate())
                .expirationDate(request.getExpirationDate().atStartOfDay())
                .build());
    }

    @Override
    public void deleteReceiptIngredient(Long receiptIngredientId) {
        receiptIngredientRepository.deleteById(receiptIngredientId);
    }
}
