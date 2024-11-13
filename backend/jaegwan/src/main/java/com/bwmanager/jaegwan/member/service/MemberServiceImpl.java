package com.bwmanager.jaegwan.member.service;

import com.bwmanager.jaegwan.global.error.ErrorCode;
import com.bwmanager.jaegwan.global.error.exception.MemberException;
import com.bwmanager.jaegwan.member.dto.MemberAndRestaurantsResponse;
import com.bwmanager.jaegwan.member.dto.MemberResponse;
import com.bwmanager.jaegwan.member.entity.Member;
import com.bwmanager.jaegwan.member.repository.MemberRepository;
import com.bwmanager.jaegwan.restaurant.entity.Restaurant;
import com.bwmanager.jaegwan.restaurant.repository.RestaurantMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final RestaurantMemberRepository restaurantMemberRepository;

    @Override
    @Transactional(readOnly = true)
    public MemberResponse getMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        return MemberResponse.from(member);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponse getMemberByEmail(String email) {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new MemberException(ErrorCode.MEMBER_NOT_FOUND);
        }

        return MemberResponse.from(member);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberAndRestaurantsResponse getMemberAndRestaurantsByEmail(String email) {
        // TODO: 56번 브랜치 병합 이후 Optional로 변경
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new MemberException(ErrorCode.MEMBER_NOT_FOUND);
        }

        List<Restaurant> restaurants = restaurantMemberRepository.findRestaurantsByMemberId(member.getId());

        return MemberAndRestaurantsResponse.of(member, restaurants);
    }

}
