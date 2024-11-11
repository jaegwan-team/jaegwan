package com.bwmanager.jaegwan.global.util;

import com.bwmanager.jaegwan.global.auth.dto.KakaoProfileResponse;
import com.bwmanager.jaegwan.global.auth.dto.KakaoTokenResponse;
import com.bwmanager.jaegwan.global.error.ErrorCode;
import com.bwmanager.jaegwan.global.error.exception.AuthException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Base64;
import java.util.Map;

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
    private final ObjectMapper objectMapper;

    /**
     * 카카오 로그인 URL을 반환한다.
     * @return 카카오 로그인 URL
     */
    public String getAuthorizationUrl() {
        return authorizationUri + "?client_id=" + clientId + "&redirect_uri="
                + redirectUri + "&response_type=code";
    }

    /**
     * 카카오 로그인 후 받은 코드를 통해 토큰을 가져온다.
     * @param kakaoCode 카카오 로그인 후 받은 코드
     * @return 카카오 인증 서버에서 받은 토큰
     */
    public KakaoTokenResponse getTokens(String kakaoCode) {
        // 요청 header를 설정한다.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 body를 설정한다.
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", kakaoCode);

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

    /**
     * 카카오에서 받은 accessToken을 통해 사용자의 카카오 프로필을 가져온다.
     * @param accessToken 카카오 인증 서버에서 받은 accessToken
     * @return 사용자의 카카오 프로필
     */
    public KakaoProfileResponse getProfile(String accessToken) {
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

    /**
     * 카카오에서 받은 idToken에서 sub와 email을 추출한다.
     * @param idToken 카카오 인증 서버에서 받은 idToken
     * @return sub와 email이 담긴 문자열 배열
     */
    public String[] getSubAndEmail(String idToken) {
        try {
            String[] parts = idToken.split("\\.");

            if (parts.length < 2) {
                throw new AuthException(ErrorCode.ID_TOKEN_FORMAT_ERROR);
            }

            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            Map<String, Object> payloadMap = objectMapper.readValue(payload, Map.class);
            String sub = (String) payloadMap.get("sub");
            String email = (String) payloadMap.get("email");

            return new String[] { sub, email };
        } catch (Exception e) {
            throw new AuthException(ErrorCode.ID_TOKEN_DECODE_FAILED);
        }
    }

}
