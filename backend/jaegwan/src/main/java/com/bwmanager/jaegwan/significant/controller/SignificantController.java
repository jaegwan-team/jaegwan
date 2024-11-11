package com.bwmanager.jaegwan.significant.controller;

import com.bwmanager.jaegwan.global.dto.CommonResponse;
import com.bwmanager.jaegwan.significant.dto.SignificantConfirmRequest;
import com.bwmanager.jaegwan.significant.dto.SignificantCreateRequest;
import com.bwmanager.jaegwan.significant.dto.SignificantReadRequest;
import com.bwmanager.jaegwan.significant.dto.TestBixby;
import com.bwmanager.jaegwan.significant.service.SignificantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/significant")
@Slf4j
public class SignificantController {

    private final SignificantService significantService;

    @Operation(summary = "특이사항 리스트 조회", description = "식당별 모든 특이사항을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상적으로 특이사항 리스트를 응답",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/list")
    public ResponseEntity<?> getSignificantsByRestaurantId(@RequestBody
                                                           SignificantReadRequest significantReadRequest) {
        System.out.println(significantReadRequest.getRestaurantId());
        CommonResponse<Object> response = CommonResponse.builder()
                .data(significantService.getSignificants(significantReadRequest))
                .message("정상적으로 식당별 특이사항 리스트를 응답")
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "빅스비 JSON 테스트", description = "JSON 형식으로 빅스비 테스트 메시지를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "JSON을 통한 빅스비 테스트",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TestBixby.class)))
    })
    @GetMapping("/bixbyByJson")
    public ResponseEntity<?> bixbyByJson() {
        TestBixby testBixby = TestBixby.builder()
                .resMessage("JSON을 통한 테스트입니다!")
                .build();
        return ResponseEntity.ok(testBixby);
    }

    @Operation(summary = "특이사항 단일 조회", description = "특정 ID의 특이사항을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상적으로 특이사항 단일값 응답",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class)))
    })
    @GetMapping("/{significantId}")
    public ResponseEntity<?> getSignificant(@Parameter(description = "조회할 특이사항 ID", required = true)
                                            @PathVariable Long significantId) {
        CommonResponse<Object> response = CommonResponse.builder()
                .data(significantService.getSignificant(significantId))
                .message("정상적으로 특이사항 단일값 응답")
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "특이사항 확인 적용", description = "특이사항을 확인 상태로 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상적으로 특이사항 확인 적용",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 데이터입니다.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 내부 에러가 발생했습니다.",
                    content = @Content)
    })
    @PutMapping
    public ResponseEntity<?> confirmSignificant(@Parameter(description = "특이사항 확인 요청 데이터", required = true)
                                                @RequestBody SignificantConfirmRequest significantConfirmRequest) {
        significantService.confirmSignificant(significantConfirmRequest);
        CommonResponse<Object> response = CommonResponse.builder()
                .message("정상적으로 특이사항 확인 적용")
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "특이사항 생성", description = "새로운 특이사항을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "특이사항으로 정보 생성",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 데이터입니다.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 내부 에러가 발생했습니다.",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createBySignificant(@Parameter(description = "특이사항 생성 요청 데이터", required = true)
                                                 @RequestBody SignificantCreateRequest significantCreateRequest) {
        CommonResponse<Object> response = CommonResponse.builder()
                .message("특이사항으로 정보 생성")
                .data(significantService.createBySignificant(significantCreateRequest))
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "특이사항 삭제", description = "특정 특이사항을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "특이사항이 성공적으로 삭제되었습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 데이터입니다.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 내부 에러가 발생했습니다.",
                    content = @Content)
    })
    @DeleteMapping
    public ResponseEntity<?> deleteBySignificant(@Parameter(description = "특이사항 삭제 요청 데이터", required = true)
                                                 @RequestBody SignificantConfirmRequest significantConfirmRequest) {
        significantService.deleteBySignificant(significantConfirmRequest);
        CommonResponse<Object> response = CommonResponse.builder()
                .message("특이사항이 성공적으로 삭제되었습니다.")
                .build();
        return ResponseEntity.ok(response);
    }
}
