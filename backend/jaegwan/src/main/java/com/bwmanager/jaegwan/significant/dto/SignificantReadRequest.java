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
@Schema(description = "식당별 모든 특이사항 조회 DTO")
public class SignificantReadRequest {

    @Schema(description = "식당 ID", example = "123", required = true)
    private Long restaurantId;
}
