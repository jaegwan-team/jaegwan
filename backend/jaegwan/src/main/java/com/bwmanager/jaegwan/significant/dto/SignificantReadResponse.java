package com.bwmanager.jaegwan.significant.dto;

import com.bwmanager.jaegwan.significant.entity.Significant;
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
public class SignificantReadResponse {

    @Schema(description = "특이사항 ID", example = "1")
    private Long id;

    @Schema(description = "특이사항 세부 내용", example = "재료 부족으로 인해 추가 주문 필요")
    private String detail;

    @Schema(description = "특이사항 확인 여부", example = "false")
    private boolean isConfirmed;

    @Schema(description = "레스토랑 ID", example = "10")
    private Long restaurantId;

    @Schema(description = "특이사항 생성 날짜", example = "2023-11-05")
    private LocalDate date;

    public static SignificantReadResponse fromEntity(Significant significant) {
        return SignificantReadResponse.builder()
                .id(significant.getId())
                .restaurantId(significant.getRestaurant().getId())
                .isConfirmed(significant.isConfirmed())
                .date(significant.getDate())
                .detail(significant.getDetail())
                .build();
    }
}
