package com.bwmanager.jaegwan.significant.dto;

import com.bwmanager.jaegwan.significant.entity.Significant;
import com.bwmanager.jaegwan.significant.entity.SignificantIngredient;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "특이사항의 조회 응답 DTO")
public class SignificantDetailReadResponse {

    @Schema(description = "특이사항 ID", example = "1")
    private Long significantId;

    @Schema(description = "특이사항 세부 내용", example = "재료 부족으로 인해 추가 주문 필요")
    private String detail;

    @Schema(description = "특이사항 확인 여부", example = "false")
    private boolean isConfirmed;

    @Schema(description = "특이사항 생성 날짜", example = "2023-11-05")
    private LocalDate date;

    @Schema(description = "특이사항 관련 재료 이름", example = "감자")
    private String ingredientName;

    @Schema(description = "관련 재료 양(개수)", example = "5")
    private double amount;

    public static SignificantDetailReadResponse of(Significant significant,
                                                   SignificantIngredient significantIngredient) {
        return SignificantDetailReadResponse.builder()
                .significantId(significant.getId())
                .detail(significant.getDetail())
                .isConfirmed(significant.isConfirmed())
                .date(significant.getDate())
                .amount(significantIngredient.getAmount())
                .ingredientName(significantIngredient.getIngredient().getName())
                .build();
    }
}
