package com.bwmanager.jaegwan.global.auth.service;

import com.bwmanager.jaegwan.global.auth.dto.AuthResponse;

public interface AuthService {

    String getAuthorizationUrl();

    AuthResponse authenticate(String code);

}
