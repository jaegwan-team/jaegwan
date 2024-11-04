package com.bwmanager.jaegwan.significant.service;

import com.bwmanager.jaegwan.significant.dto.SignificantReadResponse;
import java.util.List;

public interface SignificantService {

    /**
     * 모든 특이사항 조회
     *
     * @return List
     */
    List<SignificantReadResponse> getSignificants();

    /**
     * 특정 id에 맞는 특이사항 조회
     *
     * @param significantId
     * @return SignificantReadResponse
     */
    SignificantReadResponse getSignificant(long significantId);
}
