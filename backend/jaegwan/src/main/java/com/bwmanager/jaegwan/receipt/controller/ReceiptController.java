package com.bwmanager.jaegwan.receipt.controller;

import com.bwmanager.jaegwan.global.dto.CommonResponse;
import com.bwmanager.jaegwan.global.error.ErrorCode;
import com.bwmanager.jaegwan.global.error.exception.FileException;
import com.bwmanager.jaegwan.receipt.service.ReceiptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
                    .message("영수증 사진 업로드에 성공했습니다")
                    .build();

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            throw new FileException(ErrorCode.IMAGE_UPLOAD_ERROR);
        }
    }
}
