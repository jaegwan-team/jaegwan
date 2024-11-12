package com.bwmanager.jaegwan.global.filter;

import com.bwmanager.jaegwan.global.error.ErrorCode;
import com.bwmanager.jaegwan.global.error.exception.AuthException;
import com.bwmanager.jaegwan.global.util.JwtUtil;
import com.nimbusds.jose.shaded.gson.Gson;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private static final List<String> EXCLUDE_URLS = List.of("/api/auth", "/api/swagger-ui", "/api/api-docs");

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 요청받은 URI가 인증을 제외할 URI에 해당하는지 확인한다.
        String requestURI = request.getRequestURI();
        return EXCLUDE_URLS.stream().anyMatch(requestURI::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException {
        try {
            // Authorization 헤더에서 액세스 토큰을 가져온다.
            String accessToken = request.getHeader("Authorization");

            if (accessToken == null || accessToken.isEmpty()) {
                throw new AuthException(ErrorCode.TOKEN_NOT_VALID);
            }

            // 액세스 토큰이 유효한지 검증한다.
            Claims claims = jwtUtil.validateTokenAndgetClaims(accessToken);

            // 인증 객체를 생성한다.
            Authentication authentication = jwtUtil.getAuthentication(claims);

            // SecurityContext에 인증 객체를 설정한다.
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 다음 필터로 요청을 전달한다.
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            // 예외가 발생한 경우 예외에 대한 메시지를 JSON 형식으로 응답한다.
            Gson gson = new Gson();
            String json;

            if (e instanceof AuthException authException) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                json = gson.toJson(Map.of("code", authException.getErrorCode(), "message", e.getMessage()));
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                json = gson.toJson(Map.of("message", e.getMessage()));
            }

            response.setContentType("application/json; charset=UTF-8");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(json);
            printWriter.close();
        }
    }

}
