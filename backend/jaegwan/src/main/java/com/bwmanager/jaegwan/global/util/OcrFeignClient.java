package com.bwmanager.jaegwan.global.util;

import com.bwmanager.jaegwan.global.util.dto.OcrRequest;
import com.bwmanager.jaegwan.global.util.dto.OcrResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ocrFeignClient", url = "http://k11a501.p.ssafy.io:8000/api")
public interface OcrFeignClient {

    @PostMapping("/receipt/image")
    List<OcrResponse> imageOcr(@RequestBody OcrRequest ocrRequest);
}
