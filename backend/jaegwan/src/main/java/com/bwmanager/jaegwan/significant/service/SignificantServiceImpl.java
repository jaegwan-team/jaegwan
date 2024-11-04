package com.bwmanager.jaegwan.significant.service;

import com.bwmanager.jaegwan.restaurant.entity.Restaurant;
import com.bwmanager.jaegwan.restaurant.repository.RestaurantRepository;
import com.bwmanager.jaegwan.significant.dto.SignificantCreateRequest;
import com.bwmanager.jaegwan.significant.dto.SignificantReadResponse;
import com.bwmanager.jaegwan.significant.repository.SignificantRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignificantServiceImpl implements SignificantService {

    private final SignificantRepository significantRepository;
    private final RestaurantRepository restaurantRepository;

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
    public void createSignificant(SignificantCreateRequest significantCreateRequest) {
        Restaurant restaurant = restaurantRepository.findById(significantCreateRequest.getRestaurantId())
                .orElseThrow(EntityNotFoundException::new);
        significantRepository.save(significantCreateRequest.toEntity(restaurant));
    }
}
