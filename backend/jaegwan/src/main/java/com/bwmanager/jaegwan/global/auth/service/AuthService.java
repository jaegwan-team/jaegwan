package com.bwmanager.jaegwan.global.auth.service;

import com.bwmanager.jaegwan.global.auth.dto.AuthResponse;

public interface AuthService {

    /**
     * 카카오 로그인 페이지의 URL을 반환한다.
     * @return 카카오 로그인 페이지의 URL
     */
    String getAuthorizationUrl();

    /**
     * 카카오 로그인 후 얻은 코드를 통해 로그인 또는 회원가입을 진행한다.
     * @param code 카카오 로그인 후 얻은 코드
     * @return 로그인된 사용자 정보
     */
    AuthResponse loginOrRegister(String code);

    /**
     * 토큰을 재발급합니다.
     * @param refreshToken 기존에 발급된 리프레시 토큰
     * @return 새로운 액세스 토큰과 새로운 리프레시 토큰
     */
    AuthResponse reissue(String refreshToken);

}
