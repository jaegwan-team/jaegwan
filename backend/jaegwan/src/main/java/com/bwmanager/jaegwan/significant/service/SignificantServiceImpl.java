package com.bwmanager.jaegwan.significant.service;

import com.bwmanager.jaegwan.ingredient.entity.Ingredient;
import com.bwmanager.jaegwan.ingredient.repository.IngredientRepository;
import com.bwmanager.jaegwan.restaurant.entity.Restaurant;
import com.bwmanager.jaegwan.restaurant.repository.RestaurantRepository;
import com.bwmanager.jaegwan.significant.dto.SignificantConfirmRequest;
import com.bwmanager.jaegwan.significant.dto.SignificantCreateRequest;
import com.bwmanager.jaegwan.significant.dto.SignificantCreateResponse;
import com.bwmanager.jaegwan.significant.dto.SignificantReadResponse;
import com.bwmanager.jaegwan.significant.entity.Significant;
import com.bwmanager.jaegwan.significant.repository.SignificantIngredientRepository;
import com.bwmanager.jaegwan.significant.repository.SignificantRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignificantServiceImpl implements SignificantService {

    private final SignificantRepository significantRepository;
    private final RestaurantRepository restaurantRepository;
    private final IngredientRepository ingredientRepository;
    private final SignificantIngredientRepository significantIngredientRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SignificantReadResponse> getSignificants() {
        return significantRepository.findAll().stream().map(SignificantReadResponse::fromEntity).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SignificantReadResponse getSignificant(long significantId) {
        return SignificantReadResponse.fromEntity(
                significantRepository.findById(significantId).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    @Transactional
    public void confirmSignificant(SignificantConfirmRequest significantConfirmRequest) {
        Significant significant = significantRepository.findById(significantConfirmRequest.getSignificantId())
                .orElseThrow(EntityNotFoundException::new);
        significant.confirm();
    }

    @Override
    @Transactional
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
}
