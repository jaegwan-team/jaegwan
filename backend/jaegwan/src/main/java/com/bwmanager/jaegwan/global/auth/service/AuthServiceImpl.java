package com.bwmanager.jaegwan.global.auth.service;

import com.bwmanager.jaegwan.global.auth.dto.AuthResponse;
import com.bwmanager.jaegwan.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String authorizationUri;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    private final MemberRepository memberRepository;

    @Override
    public String getAuthorizationUrl() {
        return authorizationUri + "?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&response_type=code";
    }

    @Override
    public AuthResponse authenticate(String code) {
        return null;
    }

//    @Override
//    public AuthResponse requestToken(String code) {
//        return null;
//    }
}
