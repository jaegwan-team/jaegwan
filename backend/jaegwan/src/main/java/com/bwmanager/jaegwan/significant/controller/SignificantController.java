package com.bwmanager.jaegwan.significant.controller;

import com.bwmanager.jaegwan.global.dto.CommonResponse;
import com.bwmanager.jaegwan.significant.dto.SignificantCreateRequest;
import com.bwmanager.jaegwan.significant.dto.TestBixby;
import com.bwmanager.jaegwan.significant.service.SignificantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/significant")
@Slf4j
public class SignificantController {

    private final SignificantService significantService;

    @GetMapping
    public ResponseEntity<?> getSignificants() {
        CommonResponse<Object> response = CommonResponse.builder()
                .data(significantService.getSignificants())
                .message("정상적으로 특이사항 리스트를 응답")
                .build();
        return ResponseEntity.ok(response);
    }

    /*
     *   JSON을 통한 빅스비 테스트
     * */
    @GetMapping("/bixbyByJson")
    public ResponseEntity<?> bixbyByJson() {
        TestBixby testBixby = TestBixby.builder()
                .resMessage("JSON을 통한 테스트입니다!")
                .build();
        return ResponseEntity.ok(testBixby);
    }

    @GetMapping("/{significantId}")
    public ResponseEntity<?> getSignificant(@PathVariable long significantId) {
        CommonResponse<Object> response = CommonResponse.builder()
                .data(significantService.getSignificant(significantId))
                .message("정상적으로 특이사항 단일값 응답")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createSignificant(@RequestBody SignificantCreateRequest significantCreateRequest) {
        significantService.createSignificant(significantCreateRequest);
        return ResponseEntity.ok("정상적으로 특이사항 생성");
    }
}
