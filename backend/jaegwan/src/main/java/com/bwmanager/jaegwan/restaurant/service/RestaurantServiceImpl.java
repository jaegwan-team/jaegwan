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
    public RestaurantResponse getRestaurantById(Long id) {
        return RestaurantResponse.from(getRestaurant(id));
    }

    @Override
    public List<MemberResponse> getMembersByRestaurantId(Long id) {
        return restaurantMemberRepository.findMembersByRestaurantId(id)
                .stream().map(MemberResponse::from)
                .toList();
    }

    @Override
    public RestaurantResponse createRestaurant(RestaurantRequest request) {
        // TODO: 식당을 저장하고 식당에 사용자 등록 로직 추가

        Restaurant restaurant = Restaurant.from(request.getName(), request.getRegisterNumber());
        restaurantRepository.save(restaurant);
        return RestaurantResponse.from(restaurant);
    }

    @Override
    public void addRestaurantMember(Long restaurantId, Long memberId) {
        Restaurant restaurant = getRestaurant(restaurantId);
        Member member = getMember(memberId);
        RestaurantMember restaurantMember = RestaurantMember.from(restaurant, member);
        restaurantMemberRepository.save(restaurantMember);
    }

    private Restaurant getRestaurant(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantException(ErrorCode.RESTAURANT_NOT_FOUND));
    }

    private Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
    }

}
