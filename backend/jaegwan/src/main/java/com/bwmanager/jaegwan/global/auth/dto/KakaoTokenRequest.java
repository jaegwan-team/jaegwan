package com.bwmanager.jaegwan.global.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoTokenRequest {

    private String tokenType;

    private String accessToken;

    private int expiresIn;

    private String refreshToken;

    private int refreshTokenExpiresIn;

    private String idToken;

    private String scope;

}
