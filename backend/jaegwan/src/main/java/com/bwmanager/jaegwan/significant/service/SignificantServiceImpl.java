package com.bwmanager.jaegwan.significant.service;

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

    @Override
    public List<SignificantReadResponse> getSignificants() {
        return significantRepository.findAll().stream().map(SignificantReadResponse::fromEntity).toList();
    }

    @Override
    public SignificantReadResponse getSignificant(long significantId) {
        return SignificantReadResponse.fromEntity(
                significantRepository.findById(significantId).orElseThrow(EntityNotFoundException::new));
    }
}
