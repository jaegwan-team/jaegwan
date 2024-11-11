package com.bwmanager.jaegwan.global.auth.dto;

import com.bwmanager.jaegwan.member.entity.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthResponse {

    private String accessToken;

    private String refreshToken;

    private String name;

    private Role role;

    private String email;

    private String imageUrl;

}
