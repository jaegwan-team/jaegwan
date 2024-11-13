package com.bwmanager.jaegwan.restaurant.service;

import com.bwmanager.jaegwan.member.dto.MemberResponse;
import com.bwmanager.jaegwan.restaurant.dto.RestaurantRequest;
import com.bwmanager.jaegwan.restaurant.dto.RestaurantResponse;

import java.util.List;

public interface RestaurantService {

    /**
     * 현재 사용자가 속해있는 식당 목록을 조회합니다.
     * @param currentMemberEmail 현재 사용자 이메일
     * @return 식당 목록
     */
    List<RestaurantResponse> getMyRestaurants(String currentMemberEmail);

    /**
     * 식당 ID에 해당하는 식당의 정보를 조회한다.
     * @param currentMemberEmail 현재 사용자 이메일
     * @param id 식당 ID
     * @return 식당 ID에 해당하는 식당 정보
     */
    RestaurantResponse getRestaurant(String currentMemberEmail, Long id);

    /**
     * 식당에 속한 사용자들의 목록을 조회한다.
     * @param currentMemberEmail 현재 사용자 이메일
     * @param id 식당 ID
     * @return 식당에 속한 사용자들 목록
     */
    List<MemberResponse> getRestaurantMembers(String currentMemberEmail, Long id);

    /**
     * 식당 정보를 통해 식당을 등록한다.
     * @param currentMemberEmail 현재 사용자 이메일
     * @param request 식당 정보
     * @return 등록한 식당 정보
     */
    RestaurantResponse createRestaurant(String currentMemberEmail, RestaurantRequest request);

    /**
     * 식당 정보를 수정한다.
     * @param currentMemberEmail 현재 사용자 이메일
     * @param id 식당 ID
     * @param request 식당 정보
     * @return 수정된 식당 정보
     */
    RestaurantResponse updateRestaurant(String currentMemberEmail, Long id, RestaurantRequest request);

    /**
     * 식당을 삭제한다.
     * @param currentMemberEmail 현재 사용자 이메일
     * @param id 식당 ID
     */
    void deleteRestaurant(String currentMemberEmail, Long id);

    /**
     * 식당에 사용자를 추가한다.
     * @param currentMemberEmail 현재 사용자 이메일
     * @param id 식당 ID
     * @param newMemberId 추가할 사용자 ID
     */
    void addRestaurantMember(String currentMemberEmail, Long id, Long newMemberId);

    /**
     * 식당에서 사용자를 제외한다.
     * @param currentMemberEmail 현재 사용자 이메일
     * @param id 식당 ID
     * @param memberDeleteId 제외할 사용자 ID
     */
    void removeRestaurantMember(String currentMemberEmail, Long id, Long memberDeleteId);

}
