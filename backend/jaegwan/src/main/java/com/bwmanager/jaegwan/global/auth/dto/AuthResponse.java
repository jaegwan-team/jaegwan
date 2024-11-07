package com.bwmanager.jaegwan.global.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthResponse {

    private final String accessToken;
    private final String refreshToken;

}
