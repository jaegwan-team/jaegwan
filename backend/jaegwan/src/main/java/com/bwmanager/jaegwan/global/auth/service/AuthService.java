package com.bwmanager.jaegwan.global.auth.service;

import com.bwmanager.jaegwan.global.auth.dto.AuthResponse;

public interface AuthService {

    String getAuthorizationUrl();

    AuthResponse loginOrRegister(String code);

    AuthResponse reissue(String Token);

}
