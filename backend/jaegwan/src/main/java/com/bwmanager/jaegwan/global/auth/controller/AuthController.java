package com.bwmanager.jaegwan.global.auth.controller;

import com.bwmanager.jaegwan.global.auth.dto.AuthResponse;
import com.bwmanager.jaegwan.global.auth.service.AuthService;
import com.bwmanager.jaegwan.global.dto.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
        AuthResponse authResponse = authService.loginOrRegister(code);

        // Authorization 헤더에 액세스 토큰을 저장한다.
//        response.setHeader("Authorization", authResponse.getAccessToken());

        // 쿠키에 액세스 토큰을 저장한다.
        Cookie cookie = new Cookie("accessToken", authResponse.getAccessToken());
        cookie.setHttpOnly(false);
        cookie.setMaxAge((int) (long) accessExpiration);
        cookie.setPath("/");
        response.addCookie(cookie);

        // 쿠키에 리프레시 토큰을 저장한다.
        Cookie cookie2 = new Cookie("refreshToken", authResponse.getRefreshToken());
        cookie2.setHttpOnly(false);
        cookie2.setMaxAge((int) (long) refreshExpiration);
        cookie2.setPath("/");
        response.addCookie(cookie2);

        // 메인 페이지로 리다이렉트한다.
        response.sendRedirect(homeUri);
    }

    @Operation(summary = "토큰 재발급", description = "기존 리프레시 토큰을 통해 액세스 토큰과 리프레시 토큰을 재발급받습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 인증에 성공하여 토큰이 재발급되었습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "사용자 인증에 실패하여 토큰이 재발급되지 않았습니다.",
                    content = @Content)
    })
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestParam String refreshToken) {
        // 액세스 토큰과 리프레시 토큰을 재발급한다.
        CommonResponse<Object> response = CommonResponse.builder()
                .data(authService.reissue(refreshToken))
                .message("액세스 토큰 재발급에 성공했습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
