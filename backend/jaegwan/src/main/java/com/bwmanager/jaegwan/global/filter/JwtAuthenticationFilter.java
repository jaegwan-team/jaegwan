package com.bwmanager.jaegwan.global.filter;

import com.bwmanager.jaegwan.global.error.exception.AuthException;
import com.bwmanager.jaegwan.global.util.JwtUtil;
import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final List<String> EXCLUDE_URLS = List.of("/api/auth", "/api/swagger-ui", "/api/api-docs");

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        super();
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 요청받은 URI가 인증을 제외할 URI에 해당하는지 확인한다.
        String requestURI = request.getRequestURI();
        return EXCLUDE_URLS.stream().anyMatch(requestURI::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            // Authorization 헤더에서 액세스 토큰을 가져온다.
            String accessToken = request.getHeader("Authorization");

            // 액세스 토큰이 유효한지 검증한다. 유효하지 않을 경우 이 과정에서 예외를 발생시킨다.
            jwtUtil.validateTokenAndgetClaims(accessToken);

            // 검증이 끝나면 filter를 통과한다.
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            // 예외가 발생한 경우 예외에 대한 메시지를 JSON 형식으로 응답한다.
            Gson gson = new Gson();
            String json = "";

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
