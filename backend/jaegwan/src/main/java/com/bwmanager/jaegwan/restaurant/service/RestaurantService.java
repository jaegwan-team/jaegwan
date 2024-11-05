package com.bwmanager.jaegwan.restaurant.service;

import com.bwmanager.jaegwan.member.dto.MemberResponse;
import com.bwmanager.jaegwan.restaurant.dto.RestaurantRequest;
import com.bwmanager.jaegwan.restaurant.dto.RestaurantResponse;

import java.util.List;

public interface RestaurantService {

    /**
     * 식당 ID에 해당하는 식당의 정보를 조회한다.
     * @param id 식당 ID
     * @return 식당 ID에 해당하는 식당 정보
     */
    RestaurantResponse getRestaurantById(Long id);

    /**
     * 식당에 속한 사용자들의 목록을 조회한다.
     * @param id 식당 ID
     * @return 식당에 속한 사용자들 목록
     */
    List<MemberResponse> getMembersByRestaurantId(Long id);

    /**
     * 식당 정보를 통해 식당을 등록한다.
     * @param request 식당 정보
     * @return 등록한 식당 정보
     */
    RestaurantResponse createRestaurant(RestaurantRequest request);

    /**
     * 식당에 사용자를 추가한다.
     * @param restaurantId 식당 ID
     * @param memberId 식당에 추가할 사용자 ID
     */
    void addRestaurantMember(Long restaurantId, Long memberId);

}
