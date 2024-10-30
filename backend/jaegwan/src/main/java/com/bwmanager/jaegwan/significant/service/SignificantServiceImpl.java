package com.bwmanager.jaegwan.significant.service;


import com.bwmanager.jaegwan.significant.repository.SignificantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignificantServiceImpl implements SignificantService{

    private final SignificantRepository significantRepository;
}
