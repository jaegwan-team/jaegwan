package com.bwmanager.jaegwan.significant.controller;

import com.bwmanager.jaegwan.significant.dto.SignificantReadResponse;
import com.bwmanager.jaegwan.significant.service.SignificantService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/significant")
@Slf4j
public class SignificantController {

    private final SignificantService significantService;

    @GetMapping("")
    ResponseEntity<?> getSignificants() {
        List<SignificantReadResponse> significantReadResponses = significantService.getSignificants();
        return ResponseEntity.ok(significantReadResponses);
    }

    @GetMapping("/{significantId}")
    ResponseEntity<?> getSignificant(@PathVariable long significantId) {
        SignificantReadResponse significant = significantService.getSignificant(significantId);
        return ResponseEntity.ok(significant);
    }

}
