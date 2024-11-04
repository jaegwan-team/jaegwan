package com.bwmanager.jaegwan.restaurant.service;

import com.bwmanager.jaegwan.member.dto.MemberResponse;
import com.bwmanager.jaegwan.restaurant.dto.RestaurantResponse;

import java.util.List;

public interface RestaurantService {

    /**
     * 식당의 정보를 조회한다.
     * @param id 식당 ID
     * @return 식당 ID에 해당하는 식당 정보
     */
    public RestaurantResponse getRestaurantById(Long id);

    /**
     * 식당에 속한 사용자들의 목록을 조회한다.
     * @param restaurantId 식당 ID
     * @return 식당에 속한 사용자들 목록
     */
    public List<MemberResponse> getMembersByRestaurantId(Long restaurantId);

}
