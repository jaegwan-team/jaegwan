package com.bwmanager.jaegwan.global.util.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class OcrRequest {
    private String image_url;
}
