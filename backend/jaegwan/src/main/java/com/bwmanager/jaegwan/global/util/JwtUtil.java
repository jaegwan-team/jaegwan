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
import org.springframework.stereotype.Component;

import java.util.Date;

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
     * JWT 토큰을 생성한다.
     * @param claims JWT 토큰에 들어갈 클레임(정보)
     * @param expiration JWT 토큰의 유효 시간(밀리초 단위)
     * @return JWT 토큰
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
     * JWT 토큰에서 클레임을 추출한다.
     * @param token JWT 토큰
     * @return JWT 토큰에서 추출한 클레임 (정보)
     */
    public Claims validateTokenAndgetClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new AuthException(ErrorCode.TOKEN_EXPIRED);
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthException(ErrorCode.TOKEN_NOT_VALID);
        }
    }

}
