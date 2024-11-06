package com.bwmanager.jaegwan.receipt.controller;

import com.bwmanager.jaegwan.global.dto.CommonResponse;
import com.bwmanager.jaegwan.global.error.ErrorCode;
import com.bwmanager.jaegwan.global.error.exception.FileException;
import com.bwmanager.jaegwan.receipt.dto.ReceiptIngredientConfirmRequest;
import com.bwmanager.jaegwan.receipt.dto.ReceiptRequest;
import com.bwmanager.jaegwan.receipt.service.ReceiptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/receipt")
public class ReceiptController {
    private final ReceiptService receiptService;

    @Operation(summary = "영수증 사진 업로드", description = "id(restaurantId)와 영수증 사진 리스트가 필요합니다.")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> uploadReceipt(
            @Parameter(description = "Restaurant ID", required = true) @RequestParam("id") Long restaurantId,
            @Parameter(
                    description = "List of receipt files",
                    required = true,
                    content = @Content(mediaType = "application/form-data", schema = @Schema(type = "string", format = "binary"))
            ) @RequestParam("files") List<MultipartFile> files) {
        try {
            CommonResponse<Object> response = CommonResponse.builder()
                    .data(receiptService.saveReceipt(restaurantId, files))
                    .message("영수증 사진 업로드에 성공했습니다.")
                    .build();

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            throw new FileException(ErrorCode.IMAGE_UPLOAD_ERROR);
        }
    }

    @Operation(summary = "구매내역 목록 조회", description = "id(restaurantId)가 필요합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구매내역 목록 조회에 성공했습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 데이터입니다.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 내부 에러가 발생했습니다.",
                    content = @Content)
    })
    @PostMapping("/detail")
    public ResponseEntity<?> getReceiptsInfo(@RequestBody ReceiptRequest request) {
        CommonResponse<Object> response = CommonResponse.builder()
                .data(receiptService.getReceiptsInfo(request.getRestaurantId()))
                .message("구매 내역 조회에 성공했습니다.")
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "구매내역 상세 조회", description = "id가 필요합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구매내역 상세 조회에 성공했습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 데이터입니다.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 내부 에러가 발생했습니다.",
                    content = @Content)
    })
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getReceiptInfo(@Parameter(description = "조회할 영수증 ID", required = true, example = "10")
                                            @PathVariable("id") Long id) {
        CommonResponse<Object> response = CommonResponse.builder()
                .data(receiptService.getReceiptDetail(id))
                .message("구매 내역 상세 조회에 성공했습니다.")
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "구매 내역 확정", description = "ReceiptIngredientConfirmRequest가 필요합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구매 내역 확정에 성공했습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 데이터입니다.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 내부 에러가 발생했습니다.",
                    content = @Content)
    })
    @PostMapping("/confirm")
    public ResponseEntity<?> confirmReceiptIngredient(@RequestBody ReceiptIngredientConfirmRequest request) {

        receiptService.confirmReceiptIngredient(request);
        CommonResponse<Object> response = CommonResponse.builder()
                .message("구매 내역 재료 확정에 성공했습니다.")
                .build();

        return ResponseEntity.ok(response);
    }
}
