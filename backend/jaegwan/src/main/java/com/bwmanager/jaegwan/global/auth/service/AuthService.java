package com.bwmanager.jaegwan.global.auth.service;

import com.bwmanager.jaegwan.global.auth.dto.KakaoProfileResponse;

public interface AuthService {

    String getAuthorizationUrl();

    KakaoProfileResponse loginOrRegister(String code);

}
