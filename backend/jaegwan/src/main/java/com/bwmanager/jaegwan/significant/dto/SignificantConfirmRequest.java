package com.bwmanager.jaegwan.significant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "특이사항의 확인 기능 요청 DTO")
public class SignificantConfirmRequest {

    @Schema(description = "확인할 특이사항의 ID", example = "123", required = true)
    private Long significantId;
}
