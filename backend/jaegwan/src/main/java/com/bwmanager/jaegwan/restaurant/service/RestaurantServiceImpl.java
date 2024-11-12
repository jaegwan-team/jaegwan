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
    public RestaurantResponse getRestaurant(Long id) {
        // 식당 ID에 해당하는 식당을 가져온다. 그러한 식당이 없다면 예외를 발생시킨다.
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantException(ErrorCode.RESTAURANT_NOT_FOUND));

        // 식당 정보를 반환한다.
        return RestaurantResponse.from(restaurant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberResponse> getRestaurantMembers(Long id) {
        // 식당 ID에 해당하는 식당이 없다면 예외를 발생시킨다.
        if (!restaurantRepository.existsById(id)) {
            throw new RestaurantException(ErrorCode.RESTAURANT_NOT_FOUND);
        }

        // 식당에 속한 사용자 목록을 가져와서 반환한다.
        return restaurantMemberRepository.findMembersByRestaurantId(id)
                .stream().map(MemberResponse::from)
                .toList();
    }

    @Override
    public RestaurantResponse createRestaurant(RestaurantRequest request) {
        // TODO: 식당을 저장하고 식당에 사용자 등록 로직 추가

        // 주어진 식당 정보를 통해 식당을 생성한다.
        Restaurant restaurant = Restaurant.of(request.getName(), request.getRegisterNumber());
        restaurantRepository.save(restaurant);

        // 생성된 식당의 정보를 반환한다.
        return RestaurantResponse.from(restaurant);
    }

    @Override
    public void addRestaurantMember(Long id, Long memberId) {
        // 식당 ID에 해당하는 식당을 가져온다. 그러한 식당이 없다면 예외를 발생시킨다.
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantException(ErrorCode.RESTAURANT_NOT_FOUND));

        // 사용자 ID에 해당하는 사용자를 가져온다. 그러한 사용자가 없다면 예외를 발생시킨다.
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        // 식당-사용자 관계를 생성한다.
        RestaurantMember restaurantMember = RestaurantMember.of(restaurant, member);
        restaurantMemberRepository.save(restaurantMember);
    }

}
