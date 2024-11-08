package com.bwmanager.jaegwan.global.auth.service;

import com.bwmanager.jaegwan.global.auth.dto.AuthResponse;
import com.bwmanager.jaegwan.global.auth.dto.KakaoProfileResponse;
import com.bwmanager.jaegwan.global.auth.dto.KakaoTokenResponse;
import com.bwmanager.jaegwan.global.util.JwtUtil;
import com.bwmanager.jaegwan.global.util.KakaoUtil;
import com.bwmanager.jaegwan.member.entity.Member;
import com.bwmanager.jaegwan.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final KakaoUtil kakaoUtil;
    private final MemberRepository memberRepository;

    @Override
    public String getAuthorizationUrl() {
        return kakaoUtil.getAuthorizationUrl();
    }

    @Override
    public AuthResponse loginOrRegister(String code) {
        // 카카오 인증 서버에 요청을 보내서 토큰을 가져온다.
        KakaoTokenResponse kakaoToken = kakaoUtil.getOAuthToken(code);

        // 가져온 토큰 중 idToken을 decode하여 sub와 email을 추출한다.
        String[] decodedIdToken = jwtUtil.getSubAndEmailFromIdToken(kakaoToken.getIdToken());
        String email = decodedIdToken[1];
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            member = registerMember(kakaoToken);
        }

        return AuthResponse.builder()
                .name(member.getName())
                .role(member.getRole())
                .email(member.getEmail())
                .imageUrl(member.getImageUrl())
                .build();
    }

    private Member registerMember(KakaoTokenResponse kakaoToken) {
        KakaoProfileResponse kakaoProfile = kakaoUtil.getKakaoProfile(kakaoToken.getAccessToken());
        Member member = Member.builder()
                .name(kakaoProfile.getKakaoAccount().getProfile().getNickname())
                .email(kakaoProfile.getKakaoAccount().getEmail())
                .imageUrl(kakaoProfile.getKakaoAccount().getProfile().getProfile_image_url())
                .build();

        memberRepository.save(member);
        return member;
    }

}
