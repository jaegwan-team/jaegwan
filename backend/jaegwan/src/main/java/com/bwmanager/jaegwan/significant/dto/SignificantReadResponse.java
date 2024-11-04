package com.bwmanager.jaegwan.significant.dto;


import com.bwmanager.jaegwan.significant.entity.Significant;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignificantReadResponse {

    private long id;

    private String detail;

    private boolean isConfirmed;

    private long restaurantId;

    private Timestamp date;

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


