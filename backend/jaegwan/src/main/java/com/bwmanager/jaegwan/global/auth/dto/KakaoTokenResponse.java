package com.bwmanager.jaegwan.global.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoTokenResponse {

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("access_token")
    private final String accessToken;

    @JsonProperty("expires_in")
    private final int expiresIn;

    @JsonProperty("refresh_token")
    private final String refreshToken;

    @JsonProperty("refresh_token_expires_in")
    private final int refreshTokenExpiresIn;

    @JsonProperty("id_token")
    private final String idToken;

    @JsonProperty("scope")
    private final String scope;

}
