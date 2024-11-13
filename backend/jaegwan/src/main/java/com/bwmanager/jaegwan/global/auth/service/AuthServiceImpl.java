package com.bwmanager.jaegwan.global.auth.service;

import com.bwmanager.jaegwan.global.auth.dto.AuthResponse;
import com.bwmanager.jaegwan.global.auth.dto.KakaoProfileResponse;
import com.bwmanager.jaegwan.global.auth.dto.KakaoTokenResponse;
import com.bwmanager.jaegwan.global.error.ErrorCode;
import com.bwmanager.jaegwan.global.error.exception.AuthException;
import com.bwmanager.jaegwan.global.error.exception.MemberException;
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
        // 카카오 로그인 페이지의 URL을 반환한다.
        return kakaoUtil.getAuthorizationUrl();
    }

    @Override
    public AuthResponse loginOrRegister(String kakaoCode) {
        // 카카오 인증 서버에 요청을 보내서 토큰을 가져온다.
        KakaoTokenResponse kakaoToken = kakaoUtil.getTokens(kakaoCode);

        // 가져온 토큰 중 idToken을 decode하여 sub와 email을 추출한다.
        String[] decodedIdToken = kakaoUtil.getSubAndEmail(kakaoToken.getIdToken());

        // 추출한 이메일 정보를 통해 사용자를 조회한다.
        String email = decodedIdToken[1];
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        // 사용자 정보를 통해 액세스 토큰과 리프레시 토큰을 생성한다.
        return createTokens(member);
    }

    @Override
    public AuthResponse reissue(String refreshToken) {
        // 리프레시 토큰을 검증하고, 검증이 끝나면 리프레시 토큰에서 이메일 정보를 추출한다.
        String email = jwtUtil.validateTokenAndgetClaims(refreshToken).get("email", String.class);

        // 이메일 정보가 유효하지 않다면 토큰이 유효하지 않은 것으로 판단하여 예외를 발생시킨다.
        if (email == null || email.isBlank()) {
            throw new AuthException(ErrorCode.TOKEN_NOT_VALID);
        }

        // 추출한 이메일 정보를 통해 사용자를 조회한다.
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        // 사용자 정보를 통해 액세스 토큰과 리프레시 토큰을 재발급한다.
        return createTokens(member);
    }

    /**
     * 회원가입을 진행한다.
     * @param kakaoToken 카카오 OAuth의 액세스 토큰
     * @return 사용자 정보
     */
    private Member register(KakaoTokenResponse kakaoToken) {
        // 카카오 OAuth의 액세스 토큰을 통해 카카오 프로필 정보를 가져온다.
        KakaoProfileResponse kakaoProfile = kakaoUtil.getProfile(kakaoToken.getAccessToken());

        // 카카오 프로필 정보를 통해 새로운 사용자 정보를 생성한다.
        Member member = Member.builder()
                .name(kakaoProfile.getKakaoAccount().getProfile().getNickname())
                .email(kakaoProfile.getKakaoAccount().getEmail())
                .imageUrl(kakaoProfile.getKakaoAccount().getProfile().getProfile_image_url())
                .build();

        // 사용자 정보를 DB에 등록하고 반환한다.
        memberRepository.save(member);
        return member;
    }

    /**
     * 사용자 정보를 통해 액세스 토큰과 리프레시 토큰을 발급한다.
     * @param member 사용자 정보
     * @return 액세스 토큰과 리프레시 토큰
     */
    private AuthResponse createTokens(Member member) {
        // 사용자 정보를 이용하여 액세스 토큰과 리프레시 토큰을 생성한다.
        String accessToken = jwtUtil.createAccessToken(member.getName(), member.getEmail(), member.getRole());
        String refreshToken = jwtUtil.createRefreshToken(member.getName(), member.getEmail());

        // 토큰과 사용자 정보를 반환한다.
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
//                .name(member.getName())
//                .role(member.getRole())
//                .email(member.getEmail())
//                .imageUrl(member.getImageUrl())
                .build();
    }

}
