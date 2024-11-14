package com.bwmanager.jaegwan.significant.dto;

import com.bwmanager.jaegwan.significant.entity.Significant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
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
    private Long significantId;

    @Schema(description = "특이사항 세부 내용", example = "재료 부족으로 인해 추가 주문 필요")
    private String detail;

    @Schema(description = "특이사항 확인 여부", example = "false")
    private boolean isConfirmed;

    @Schema(description = "특이사항 생성 날짜", example = "2023-11-05 12:12")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime date;

    public static SignificantReadResponse fromEntity(Significant significant) {
        return SignificantReadResponse.builder()
                .significantId(significant.getId())
                .isConfirmed(significant.isConfirmed())
                .date(significant.getDate())
                .detail(significant.getDetail())
                .build();
    }
}
