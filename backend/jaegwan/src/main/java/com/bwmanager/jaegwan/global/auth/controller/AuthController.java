package com.bwmanager.jaegwan.global.auth.controller;

import com.bwmanager.jaegwan.global.auth.dto.*;
import com.bwmanager.jaegwan.global.auth.service.AuthService;
import com.bwmanager.jaegwan.global.dto.CommonResponse;
import com.bwmanager.jaegwan.global.error.ErrorCode;
import com.bwmanager.jaegwan.global.error.exception.AuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @Value("${home-uri}")
    private String homeUri;

    @Value("${jwt.access.expiration}")
    private Long accessExpiration;

    @Value("${jwt.refresh.expiration}")
    private Long refreshExpiration;

    private final AuthService authService;

    @Operation(summary = "카카오 로그인 페이지 연결", description = "카카오 로그인 페이지로 연결합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "카카오 OAuth 인증을 위해 카카오 로그인 페이지로 리다이렉트되었습니다.",
                    content = @Content)
    })
    @GetMapping("/kakao/login")
    public ResponseEntity<?> loginKakao() {
        // 카카오 로그인 페이지로 리다이렉트할 URL을 가져온다.
        String authorizationUrl = authService.getAuthorizationUrl();

        // 리다이렉트 URL을 응답한다.
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", authorizationUrl).build();
    }

    @Operation(summary = "카카오 인증 후 로그인 또는 회원가입", description = "카카오 인증 정보를 통해 로그인 또는 회원가입을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "로그인 성공 후 메인 페이지로 리다이렉트되었습니다.",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "로그인 또는 회원가입에 실패했습니다.",
                    content = @Content)
    })
    @GetMapping("/kakao/callback")
    public void loginOrRegister(@RequestParam String code, HttpServletResponse response) throws IOException {
        // 카카오 인증 코드를 통해 사용자 정보를 확인 후 토큰을 발급한다.
        // 등록되지 않은 사용자라면 회원가입 진행 후 토큰을 발급한다.
        AuthResponse authResponse = authService.loginOrRegister(code);

        // Authorization 헤더에 액세스 토큰을 저장한다.
//        response.setHeader("Authorization", authResponse.getAccessToken());

        // 쿠키에 액세스 토큰과 리프레시 토큰을 추가한다.
        addTokensToCookie(response, authResponse);

        // 메인 페이지로 리다이렉트한다.
        response.sendRedirect(homeUri);
    }

    @GetMapping("/kakao/app")
    public void loginOrRegisterForApp(@RequestBody KakaoTokenRequest request, HttpServletResponse response)
            throws IOException {
        AuthResponse authResponse = authService.loginOrRegisterForApp(request);

        // 쿠키에 액세스 토큰과 리프레시 토큰을 추가한다.
        addTokensToCookie(response, authResponse);

        // 메인 페이지로 리다이렉트한다.
        response.sendRedirect(homeUri);
    }

    @Operation(summary = "토큰 재발급", description = "기존 리프레시 토큰을 통해 액세스 토큰과 리프레시 토큰을 재발급받습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 인증에 성공하여 토큰이 재발급되었습니다.",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "사용자 인증에 실패하여 토큰이 재발급되지 않았습니다.",
                    content = @Content)
    })
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .orElseThrow(() -> new AuthException(ErrorCode.TOKEN_NOT_FOUND))
                .getValue();

        // 액세스 토큰과 리프레시 토큰을 재발급한다.
        AuthResponse authResponse = authService.reissue(refreshToken);

        // 쿠키에 액세스 토큰과 리프레시 토큰을 추가한다.
        addTokensToCookie(response, authResponse);

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .message("액세스 토큰 재발급에 성공했습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    /**
     * 쿠키에 액세스 토큰과 리프레시 토큰을 추가한다.
     * @param response 클라이언트에 전송할 응답
     * @param authResponse 액세스 토큰과 리프레시 토큰 DTO
     */
    private void addTokensToCookie(HttpServletResponse response, AuthResponse authResponse) {
        // 액세스 토큰 쿠키를 설정한다.
        Cookie accessTokenCookie = new Cookie("accessToken", authResponse.getAccessToken());
        accessTokenCookie.setHttpOnly(false);
        accessTokenCookie.setMaxAge(accessExpiration.intValue());
        accessTokenCookie.setPath("/");

        // 리프레시 토큰 쿠키를 설정한다.
        Cookie refreshTokenCookie = new Cookie("refreshToken", authResponse.getRefreshToken());
        refreshTokenCookie.setHttpOnly(false);
        refreshTokenCookie.setMaxAge(refreshExpiration.intValue());
        refreshTokenCookie.setPath("/");

        // 응답에 쿠키를 추가한다.
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

}
