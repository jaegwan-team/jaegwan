package com.bwmanager.jaegwan.global.util;

import com.bwmanager.jaegwan.global.error.ErrorCode;
import com.bwmanager.jaegwan.global.error.exception.AuthException;
import com.bwmanager.jaegwan.member.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${jwt.token.secret-key}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessExpiration;

    @Value("${jwt.refresh.expiration}")
    private Long refreshExpiration;

    private static final String BEARER = "Bearer ";

    /**
     * 액세스 토큰을 생성한다.
     * @param memberName 사용자 이름
     * @param email 사용자 이메일
     * @param role 사용자 권한
     * @return 액세스 토큰
     */
    public String createAccessToken(String memberName, String email, Role role) {
        if (memberName == null || email == null || role == null) {
            throw new AuthException(ErrorCode.JWT_CREATE_INVALID_INPUT);
        }

        Claims claims = Jwts.claims();
        claims.put("memberName", memberName);
        claims.put("email", email);
        claims.put("role", role.name());
        return createToken(claims, accessExpiration);
    }

    /**
     * 리프레시 토큰을 생성한다.
     * @param memberName 사용자 이름
     * @param email 사용자 이메일
     * @return 리프레시 토큰
     */
    public String createRefreshToken(String memberName, String email) {
        if (memberName == null || email == null) {
            throw new AuthException(ErrorCode.JWT_CREATE_INVALID_INPUT);
        }

        Claims claims = Jwts.claims();
        claims.put("memberName", memberName);
        claims.put("email", email);
        return createToken(claims, refreshExpiration);
    }

    /**
     * JWT를 생성한다.
     * @param claims JWT에 들어갈 클레임(정보)
     * @param expiration JWT의 유효 시간(밀리초 단위)
     * @return JWT
     */
    private String createToken(Claims claims, long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * JWT에서 클레임을 추출한다.
     * @param token JWT
     * @return JWT에서 추출한 클레임 (정보)
     */
    public Claims validateTokenAndgetClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            throw new AuthException(ErrorCode.TOKEN_EXPIRED);
        } catch (JwtException | IllegalArgumentException e) {
            // 토큰 파싱 중 문제가 생겼거나 토큰이 유효하지 않은 경우
            throw new AuthException(ErrorCode.TOKEN_NOT_VALID);
        }
    }

    /**
     * 클레임을 통해 Authentication 객체를 생성하여 반환한다.
     * @param claims 클레임
     * @return Authentication 객체
     */
    public Authentication getAuthentication(Claims claims) {
        // 토큰을 검증하고 토큰에서 사용자 이메일을 추출한다.
        String email = claims.get("email", String.class);

        if (email == null || email.isEmpty()) {
            throw new AuthException(ErrorCode.TOKEN_NOT_VALID);
        }

        // 사용자 이메일이 담겨있는 Authentication 객체를 반환한다.
        return new UsernamePasswordAuthenticationToken(email, null, List.of());
    }

}
