package com.bwmanager.jaegwan.global.util;

import com.bwmanager.jaegwan.global.util.dto.OcrRequest;
import com.bwmanager.jaegwan.global.util.dto.OcrResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestClientUtil {

    private final RestClient restClient = RestClient.create();

//    private static final String BASE = "http://k11a501.p.ssafy.io:8000/api/";
    private static final String BASE = "http://localhost:8000/api/";

    public OcrResponse imageOcr(String imageUrl) {
        String apiName = "receipt/image";

        OcrRequest request = OcrRequest.builder()
                .image_url(imageUrl)
                .build();

        log.info("request -> {}", request.toString());

        return executePost(apiName, request, OcrResponse.class).getBody();
    }

    private <T> ResponseEntity<T> executePost(String apiName, Object requestBody, Class<T> responseType) {
        return restClient.post()
                .uri(BASE + apiName)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .toEntity(responseType);
    }
}
