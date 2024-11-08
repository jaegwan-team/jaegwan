package com.bwmanager.jaegwan.global.auth.service;

import com.bwmanager.jaegwan.global.auth.dto.KakaoProfileResponse;
import com.bwmanager.jaegwan.global.auth.dto.KakaoTokenResponse;
import com.bwmanager.jaegwan.global.util.KakaoUtil;
import com.bwmanager.jaegwan.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final KakaoUtil kakaoUtil;
    private final MemberRepository memberRepository;

    @Override
    public String getAuthorizationUrl() {
        return kakaoUtil.getAuthorizationUrl();
    }

    @Override
    public KakaoProfileResponse loginOrRegister(String code) {
        KakaoTokenResponse kakaoToken = kakaoUtil.getOAuthToken(code);
        KakaoProfileResponse kakaoProfile = kakaoUtil.getKakaoProfile(kakaoToken.getAccessToken());

        return kakaoProfile;
    }

}
