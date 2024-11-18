package com.bwmanager.jaegwan.significant.service;

import com.bwmanager.jaegwan.ingredient.entity.Ingredient;
import com.bwmanager.jaegwan.ingredient.entity.IngredientDetail;
import com.bwmanager.jaegwan.ingredient.repository.IngredientDetailRepository;
import com.bwmanager.jaegwan.ingredient.repository.IngredientRepository;
import com.bwmanager.jaegwan.restaurant.entity.Restaurant;
import com.bwmanager.jaegwan.restaurant.repository.RestaurantRepository;
import com.bwmanager.jaegwan.significant.dto.SignificantConfirmRequest;
import com.bwmanager.jaegwan.significant.dto.SignificantCreateRequest;
import com.bwmanager.jaegwan.significant.dto.SignificantCreateResponse;
import com.bwmanager.jaegwan.significant.dto.SignificantDetailReadResponse;
import com.bwmanager.jaegwan.significant.dto.SignificantReadRequest;
import com.bwmanager.jaegwan.significant.dto.SignificantReadResponse;
import com.bwmanager.jaegwan.significant.entity.Significant;
import com.bwmanager.jaegwan.significant.entity.SignificantIngredient;
import com.bwmanager.jaegwan.significant.repository.SignificantIngredientRepository;
import com.bwmanager.jaegwan.significant.repository.SignificantRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SignificantServiceImpl implements SignificantService {

    private final SignificantRepository significantRepository;
    private final RestaurantRepository restaurantRepository;
    private final IngredientRepository ingredientRepository;
    private final SignificantIngredientRepository significantIngredientRepository;
    private final IngredientDetailRepository ingredientDetailRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SignificantReadResponse> getSignificants(
            SignificantReadRequest significantReadRequest) {
        ArrayList<SignificantReadResponse> result = new ArrayList<>(
                significantRepository.findAllByRestaurantId(
                                significantReadRequest.getRestaurantId())
                        .stream()
                        .map(SignificantReadResponse::fromEntity).toList());
        Collections.reverse(result);
        return result;
    }


    @Override
    public List<SignificantReadResponse> getUncheckedSignificants(SignificantReadRequest significantReadRequest) {
        return significantRepository.findAllByRestaurantIdAndIsConfirmed(significantReadRequest.getRestaurantId(),
                        false)
                .stream().map(SignificantReadResponse::fromEntity).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SignificantDetailReadResponse> getSignificant(Long significantId) {
        Significant significant = significantRepository.findById(significantId)
                .orElseThrow(EntityNotFoundException::new);
        return significantIngredientRepository.findAllBySignificantId(significantId).stream()
                .map(significantIngredient -> SignificantDetailReadResponse.of(significant, significantIngredient))
                .toList();
    }

    @Override
    public void confirmSignificant(SignificantConfirmRequest significantConfirmRequest) {
        Significant significant = significantRepository.findById(significantConfirmRequest.getSignificantId())
                .orElseThrow(EntityNotFoundException::new);
        if (validateAmountAndProcess(significant)) {
            significant.confirm();
        }
    }

    private boolean validateAmountAndProcess(Significant significant) {
        List<SignificantIngredient> significantIngredients = significantIngredientRepository.findAllBySignificantId(
                significant.getId());
        for (SignificantIngredient significantIngredient : significantIngredients) {
            List<IngredientDetail> ingredientDetails = ingredientDetailRepository.findAllByIngredientIdAndIngredient_Restaurant_id(
                    significantIngredient.getIngredient().getId(), significant.getRestaurant().getId());
            if (!validateAmountSumAndProcess(significantIngredient, ingredientDetails)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateAmountSumAndProcess(SignificantIngredient significantIngredient,
                                                List<IngredientDetail> ingredientDetails) {
        double sum = ingredientDetails.stream().mapToDouble(IngredientDetail::getAmount).sum();
        if (significantIngredient.getAmount() <= sum) {
            processAmount(significantIngredient.getAmount(), ingredientDetails);
            return true;
        }
        return false;
    }

    private void processAmount(double amount, List<IngredientDetail> ingredientDetails) {
        for (IngredientDetail ingredientDetail : ingredientDetails) {
            if (ingredientDetail.getAmount() <= amount) {
                ingredientDetailRepository.delete(ingredientDetail);
                amount -= ingredientDetail.getAmount();
            } else {
                ingredientDetail.decreaseAmount(amount);
            }
        }
    }

    @Override
    public SignificantCreateResponse createBySignificant(SignificantCreateRequest significantCreateRequest) {
        Ingredient ingredient = ingredientRepository.findByRestaurantIdAndName(
                significantCreateRequest.getRestaurantId(),
                significantCreateRequest.getIngredientName()).orElseThrow(EntityNotFoundException::new);
        saveBySignificant(significantCreateRequest, ingredient);
        return significantCreateRequest.toSignificantCreateResponse();
    }

    private void saveBySignificant(SignificantCreateRequest significantCreateRequest, Ingredient ingredient) {
        Significant significant = saveSignificant(significantCreateRequest);
        significantIngredientRepository.save(significantCreateRequest.toSignificantIngredient(significant, ingredient));
    }

    private Significant saveSignificant(SignificantCreateRequest significantCreateRequest) {
        Restaurant restaurant = restaurantRepository.findById(significantCreateRequest.getRestaurantId())
                .orElseThrow(EntityNotFoundException::new);
        return significantRepository.save(significantCreateRequest.toSignificant(restaurant));
    }

    @Override
    public void deleteBySignificant(SignificantConfirmRequest significantConfirmRequest) {
        Long significantId = significantConfirmRequest.getSignificantId();
        if (!significantRepository.existsById(significantId)) {
            throw new EntityNotFoundException("[deleteBySignificant] Significant id 가 존재하지 않습니다!]");
        }
        significantIngredientRepository.deleteBySignificantId(significantConfirmRequest.getSignificantId());
        significantRepository.deleteById(significantId);
    }
}
