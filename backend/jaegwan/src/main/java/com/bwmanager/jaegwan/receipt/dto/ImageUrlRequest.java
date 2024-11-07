package com.bwmanager.jaegwan.receipt.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageUrlRequest {
    private Long restaurantId;
    private String imageUrl;
}
