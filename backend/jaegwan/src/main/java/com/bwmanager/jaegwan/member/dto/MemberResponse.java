package com.bwmanager.jaegwan.member.dto;

import com.bwmanager.jaegwan.member.entity.Member;
import com.bwmanager.jaegwan.member.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "사용자 정보 DTO")
public class MemberResponse {

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

    public static MemberResponse from(final Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .role(member.getRole())
                .email(member.getEmail())
                .imageUrl(member.getImageUrl())
                .build();
    }

}
