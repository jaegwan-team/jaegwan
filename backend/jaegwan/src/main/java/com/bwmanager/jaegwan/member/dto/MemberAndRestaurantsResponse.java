package com.bwmanager.jaegwan.member.dto;

import com.bwmanager.jaegwan.member.entity.Member;
import com.bwmanager.jaegwan.member.entity.Role;
import com.bwmanager.jaegwan.restaurant.dto.RestaurantResponse;
import com.bwmanager.jaegwan.restaurant.entity.Restaurant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "사용자 정보와 식당 목록 DTO")
public class MemberAndRestaurantsResponse {

    @Schema(description = "사용자 ID", example = "1")
    private Long id;

    @Schema(description = "사용자 이름(닉네임)", example = "홍길동")
    private String name;

    @Schema(description = "사용자 권한", example = "USER")
    private Role role;

    @Schema(description = "사용자 이메일", example = "abc123@naver.com")
    private String email;

    @Schema(description = "사용자 카카오 프로필 이미지 URL",
            example = "http://k.kakaocdn.net/dn/.../img_640x640.jpg")
    private String imageUrl;

    @Schema(description = "식당 목록")
    private List<RestaurantResponse> restaurants;

    public static MemberAndRestaurantsResponse of(final Member member, final List<Restaurant> restaurants) {
        return MemberAndRestaurantsResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .role(member.getRole())
                .email(member.getEmail())
                .imageUrl(member.getImageUrl())
                .restaurants(restaurants.stream().map(RestaurantResponse::from).toList())
                .build();
    }

}
