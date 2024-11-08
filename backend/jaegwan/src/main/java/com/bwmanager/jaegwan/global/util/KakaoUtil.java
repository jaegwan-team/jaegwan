package com.bwmanager.jaegwan.global.util;

import com.bwmanager.jaegwan.global.auth.dto.KakaoProfileResponse;
import com.bwmanager.jaegwan.global.auth.dto.KakaoTokenResponse;
import com.bwmanager.jaegwan.global.error.ErrorCode;
import com.bwmanager.jaegwan.global.error.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class KakaoUtil {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String authorizationUri;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUri;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String userInfoUri;

    private final RestClient restClient;

    public String getAuthorizationUrl() {
        return authorizationUri + "?client_id=" + clientId + "&redirect_uri="
                + redirectUri + "&response_type=code";
    }

    public KakaoTokenResponse getOAuthToken(String code) {
        // 요청 header를 설정한다.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 body를 설정한다.
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        // 카카오 인증 서버로 POST 요청을 보내고 액세스 토큰과 리프레시 토큰을 응답받는다.
        KakaoTokenResponse KakaoTokenResponse = restClient
                .post()
                .uri(tokenUri)
                .headers(h -> h.addAll(headers))
                .body(body)
                .retrieve()
                .body(KakaoTokenResponse.class);

        if (KakaoTokenResponse == null) {
            throw new AuthException(ErrorCode.AUTHENTICATION_FAILED);
        }

        return KakaoTokenResponse;
    }

    public KakaoProfileResponse getKakaoProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        KakaoProfileResponse kakaoProfileResponse = restClient
                .post()
                .uri(userInfoUri)
                .headers(h -> h.addAll(headers))
                .retrieve()
                .body(KakaoProfileResponse.class);

        if (kakaoProfileResponse == null) {
            throw new AuthException(ErrorCode.AUTHENTICATION_FAILED);
        }

        return kakaoProfileResponse;
    }

}
