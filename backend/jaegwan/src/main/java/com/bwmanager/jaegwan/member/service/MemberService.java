package com.bwmanager.jaegwan.member.service;

import com.bwmanager.jaegwan.member.dto.MemberAndRestaurantsResponse;
import com.bwmanager.jaegwan.member.dto.MemberResponse;

public interface MemberService {

    /**
     * 사용자 정보를 조회한다.
     * @param id 사용자 ID
     * @return 사용자 정보
     */
    MemberResponse getMember(Long id);

    /**
     * 이메일을 통해 사용자 정보를 조회한다.
     * @param email 사용자 이메일
     * @return 사용자 정보
     */
    MemberResponse getMemberByEmail(String email);

    /**
     * 이메일을 통해 사용자 정보와 사용자가 속한 식당 목록을 조회한다.
     * @param email 사용자 이메일
     * @return 사용자 정보와 사용자가 속한 식당 목록
     */
    MemberAndRestaurantsResponse getMemberAndRestaurantsByEmail(String email);

}
