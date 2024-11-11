package com.bwmanager.jaegwan.global.auth.controller;

import com.bwmanager.jaegwan.global.auth.service.AuthService;
import com.bwmanager.jaegwan.global.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/kakao/login")
    public ResponseEntity<?> loginKakao() {
        // 카카오 로그인 페이지로 리다이렉트할 URL을 가져온다.
        String authorizationUrl = authService.getAuthorizationUrl();

        // 리다이렉트 URL을 응답한다.
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", authorizationUrl).build();
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<?> loginOrRegister(@RequestParam String code) {
        CommonResponse<Object> response = CommonResponse.builder()
                .data(authService.loginOrRegister(code))
                .message("로그인에 성공했습니다.")
                .build();

        // 로그인 정보를 응답한다.
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestParam String refreshToken) {
        System.out.println(refreshToken);
        CommonResponse<Object> response = CommonResponse.builder()
                .data(authService.reissue(refreshToken))
                .message("액세스 토큰 재발급에 성공했습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
