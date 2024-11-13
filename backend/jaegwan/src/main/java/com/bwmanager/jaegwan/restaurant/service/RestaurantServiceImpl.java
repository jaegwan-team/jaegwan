package com.bwmanager.jaegwan.restaurant.service;

import com.bwmanager.jaegwan.global.error.ErrorCode;
import com.bwmanager.jaegwan.global.error.exception.MemberException;
import com.bwmanager.jaegwan.global.error.exception.RestaurantException;
import com.bwmanager.jaegwan.member.dto.MemberResponse;
import com.bwmanager.jaegwan.member.entity.Member;
import com.bwmanager.jaegwan.member.repository.MemberRepository;
import com.bwmanager.jaegwan.restaurant.dto.RestaurantRequest;
import com.bwmanager.jaegwan.restaurant.dto.RestaurantResponse;
import com.bwmanager.jaegwan.restaurant.entity.Restaurant;
import com.bwmanager.jaegwan.restaurant.entity.RestaurantMember;
import com.bwmanager.jaegwan.restaurant.repository.RestaurantMemberRepository;
import com.bwmanager.jaegwan.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMemberRepository restaurantMemberRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RestaurantResponse> getMyRestaurants(String currentMemberEmail) {
        // 현재 사용자 정보를 가져온다.
        Member currentMember = memberRepository.findByEmail(currentMemberEmail)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        return restaurantMemberRepository.findRestaurantsByMemberId(currentMember.getId())
                .stream().map(RestaurantResponse::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RestaurantResponse getRestaurant(String currentMemberEmail, Long id) {
        // 현재 사용자 정보를 가져온다.
        Member currentMember = memberRepository.findByEmail(currentMemberEmail)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        // 식당에 현재 사용자가 등록되어 있는지 검증한다.
        checkRestaurantAuthorized(currentMember.getId(), id);

        // 식당 정보를 가져온다.
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantException(ErrorCode.RESTAURANT_NOT_FOUND));

        // 식당 정보를 반환한다.
        return RestaurantResponse.from(restaurant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberResponse> getRestaurantMembers(String currentMemberEmail, Long id) {
        // 현재 사용자 정보를 가져온다.
        Member currentMember = memberRepository.findByEmail(currentMemberEmail)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        // 식당에 현재 사용자가 등록되어 있는지 검증한다.
        checkRestaurantAuthorized(currentMember.getId(), id);

        // 식당 ID에 해당하는 식당이 존재하는지 검증한다.
        if (!restaurantRepository.existsById(id)) {
            throw new RestaurantException(ErrorCode.RESTAURANT_NOT_FOUND);
        }

        // 식당에 속한 사용자 목록을 반환한다.
        return restaurantMemberRepository.findMembersByRestaurantId(id)
                .stream().map(MemberResponse::from)
                .toList();
    }

    @Override
    public RestaurantResponse createRestaurant(String currentMemberEmail, RestaurantRequest request) {
        // 현재 사용자 정보를 가져온다.
        Member currentMember = memberRepository.findByEmail(currentMemberEmail)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        // 식당 정보를 통해 식당을 생성한다.
        Restaurant restaurant = Restaurant.of(request.getName(), request.getRegisterNumber());
        restaurantRepository.save(restaurant);

        // 생성된 식당에 사용자를 추가한다.
        restaurantMemberRepository.save(RestaurantMember.of(restaurant, currentMember));

        // 생성한 식당의 정보를 반환한다.
        return RestaurantResponse.from(restaurant);
    }

    @Override
    public RestaurantResponse updateRestaurant(String currentMemberEmail, Long id, RestaurantRequest request) {
        // 현재 사용자 정보를 가져온다.
        Member currentMember = memberRepository.findByEmail(currentMemberEmail)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        // 식당에 현재 사용자가 등록되어 있는지 검증한다.
        checkRestaurantAuthorized(currentMember.getId(), id);

        // 식당 정보를 가져온다.
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantException(ErrorCode.RESTAURANT_NOT_FOUND));

        // 식당 정보를 업데이트한다.
        restaurant.updateRestaurant(request.getName(), request.getRegisterNumber());

        // 수정한 식당 정보를 반환한다.
        return RestaurantResponse.from(restaurant);
    }

    @Override
    public void deleteRestaurant(String currentMemberEmail, Long id) {
        // 현재 사용자 정보를 가져온다.
        Member currentMember = memberRepository.findByEmail(currentMemberEmail)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        // 식당에 현재 사용자가 등록되어 있는지 검증한다.
        checkRestaurantAuthorized(currentMember.getId(), id);

        // 식당 정보를 가져온다.
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantException(ErrorCode.RESTAURANT_NOT_FOUND));

        restaurantRepository.delete(restaurant);
    }

    @Override
    public void addRestaurantMember(String currentMemberEmail, Long id, Long newMemberId) {
        // 현재 사용자 정보를 가져온다.
        Member currentMember = memberRepository.findByEmail(currentMemberEmail)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        // 식당에 현재 사용자가 등록되어 있는지 검증한다.
        checkRestaurantAuthorized(currentMember.getId(), id);

        // 식당 정보를 가져온다.
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantException(ErrorCode.RESTAURANT_NOT_FOUND));

        // 추가할 사용자 정보를 가져온다.
        Member newMember = memberRepository.findById(newMemberId)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        // 식당-사용자 관계를 생성한다.
        restaurantMemberRepository.save(RestaurantMember.of(restaurant, newMember));
    }

    @Override
    public void removeRestaurantMember(String currentMemberEmail, Long id, Long memberDeleteId) {
        // 현재 사용자 정보를 가져온다.
        Member currentMember = memberRepository.findByEmail(currentMemberEmail)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        // 식당에 현재 사용자가 등록되어 있는지 검증한다.
        checkRestaurantAuthorized(currentMember.getId(), id);

        // 식당 정보를 가져온다.
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantException(ErrorCode.RESTAURANT_NOT_FOUND));

        // 제외할 사용자 정보를 가져온다.
        Member memberDelete = memberRepository.findById(memberDeleteId)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        // 제거할 식당-사용자 관계를 가져온다.
        RestaurantMember restaurantMemberDelete = restaurantMemberRepository
                .findByMemberAndRestaurant(memberDelete,restaurant)
                .orElseThrow(() -> new RestaurantException(ErrorCode.RESTAURANT_MEMBER_NOT_FOUND));

        // 식당 사용자 관계를 제거한다.
        restaurantMemberRepository.delete(restaurantMemberDelete);
    }

    /**
     * 식당에 사용자가 등록되어 있는지 검증한다.
     * @param memberId 사용자 ID
     * @param restaurantId 식당 ID
     */
    private void checkRestaurantAuthorized(Long memberId, Long restaurantId) {
        if (!restaurantMemberRepository.existsByMemberIdAndRestaurantId(memberId, restaurantId)) {
            throw new RestaurantException(ErrorCode.RESTAURANT_UNAUTHORIZED);
        }
    }

}
