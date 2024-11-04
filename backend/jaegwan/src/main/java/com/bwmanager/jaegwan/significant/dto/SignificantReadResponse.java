package com.bwmanager.jaegwan.significant.dto;


import com.bwmanager.jaegwan.significant.entity.Significant;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignificantReadResponse {

    private Long id;

    private String detail;

    private boolean isConfirmed;

    private long restaurantId;

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


