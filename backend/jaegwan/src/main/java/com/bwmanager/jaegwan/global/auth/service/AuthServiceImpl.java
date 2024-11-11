package com.bwmanager.jaegwan.global.auth.service;

import com.bwmanager.jaegwan.global.auth.dto.AuthResponse;
import com.bwmanager.jaegwan.global.auth.dto.KakaoProfileResponse;
import com.bwmanager.jaegwan.global.auth.dto.KakaoTokenResponse;
import com.bwmanager.jaegwan.global.error.ErrorCode;
import com.bwmanager.jaegwan.global.error.exception.AuthException;
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
    public AuthResponse loginOrRegister(String kakaoCode) {
        // 카카오 인증 서버에 요청을 보내서 토큰을 가져온다.
        KakaoTokenResponse kakaoToken = kakaoUtil.getTokens(kakaoCode);

        // 가져온 토큰 중 idToken을 decode하여 sub와 email을 추출한다.
        String[] decodedIdToken = kakaoUtil.getSubAndEmail(kakaoToken.getIdToken());
        String email = decodedIdToken[1];
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            member = register(kakaoToken);
        }

        return createTokens(member);
    }

    @Override
    public AuthResponse reissue(String refreshToken) {
        if (!jwtUtil.isTokenValid(refreshToken)) {
            throw new AuthException(ErrorCode.REFRESH_TOKEN_NOT_VALID);
        }

        String email = jwtUtil.getClaims(refreshToken).get("email", String.class);
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new AuthException(ErrorCode.REFRESH_TOKEN_NOT_VALID);
        }

        return createTokens(member);
    }

    private Member register(KakaoTokenResponse kakaoToken) {
        KakaoProfileResponse kakaoProfile = kakaoUtil.getProfile(kakaoToken.getAccessToken());
        Member member = Member.builder()
                .name(kakaoProfile.getKakaoAccount().getProfile().getNickname())
                .email(kakaoProfile.getKakaoAccount().getEmail())
                .imageUrl(kakaoProfile.getKakaoAccount().getProfile().getProfile_image_url())
                .build();

        memberRepository.save(member);
        return member;
    }

    private AuthResponse createTokens(Member member) {
        String accessToken = jwtUtil.createAccessToken(member.getName(), member.getEmail(), member.getRole());
        String refreshToken = jwtUtil.createRefreshToken(member.getName(), member.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .name(member.getName())
                .role(member.getRole())
                .email(member.getEmail())
                .imageUrl(member.getImageUrl())
                .build();
    }

}
