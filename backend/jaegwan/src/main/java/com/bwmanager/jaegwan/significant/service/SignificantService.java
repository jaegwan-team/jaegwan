package com.bwmanager.jaegwan.significant.service;

import com.bwmanager.jaegwan.significant.dto.SignificantConfirmRequest;
import com.bwmanager.jaegwan.significant.dto.SignificantCreateRequest;
import com.bwmanager.jaegwan.significant.dto.SignificantCreateResponse;
import com.bwmanager.jaegwan.significant.dto.SignificantDetailReadResponse;
import com.bwmanager.jaegwan.significant.dto.SignificantReadRequest;
import com.bwmanager.jaegwan.significant.dto.SignificantReadResponse;
import java.util.List;

public interface SignificantService {

    /**
     * 모든 특이사항 조회
     *
     * @return List
     */
    List<SignificantReadResponse> getSignificants(
            SignificantReadRequest significantReadRequest);

    /**
     * 특정 id에 맞는 특이사항 조회
     *
     * @param significantId
     * @return SignificantReadResponse
     */
    List<SignificantDetailReadResponse> getSignificant(Long significantId);

    /**
     * 저장된 특이사항 확인을 처리
     *
     * @param significantConfirmRequest 특이사항의 id
     */
    void confirmSignificant(SignificantConfirmRequest significantConfirmRequest);

    /**
     * 특이사항이 입력되면 해당 내용에 대한 재료가 존재하는지를 확인
     *
     * @param significantCreateRequest 어떤 재료를 얼마나 감소시킬지에 대한 정보
     * @return 입력한 정보에 대한 값을 리턴 -> 어떤 정보가 저장되었는지를 보여주기위함
     */
    SignificantCreateResponse createBySignificant(SignificantCreateRequest significantCreateRequest);

    /**
     * 사용자가 특이사항을 제거할때 사용하는 로직
     *
     * @param significantConfirmRequest 어떤 특이사항을 제거할지에 대한 특이사항 id 값
     */
    void deleteBySignificant(SignificantConfirmRequest significantConfirmRequest);

    List<SignificantReadResponse> getUncheckedSignificants(SignificantReadRequest significantReadRequest);
}