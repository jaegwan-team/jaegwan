package com.bwmanager.jaegwan.global.auth.controller;

import com.bwmanager.jaegwan.global.auth.dto.AuthResponse;
import com.bwmanager.jaegwan.global.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
        String redirectUrl = authService.getAuthorizationUrl();

        return ResponseEntity.status(HttpStatus.FOUND).header("Location", redirectUrl).build();
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<?> callbackKakao(@RequestParam String code) {
        AuthResponse response = authService.authenticate(code);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
