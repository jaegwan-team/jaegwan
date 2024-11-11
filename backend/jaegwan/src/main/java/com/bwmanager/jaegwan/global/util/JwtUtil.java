package com.bwmanager.jaegwan.global.util;

import com.bwmanager.jaegwan.global.error.ErrorCode;
import com.bwmanager.jaegwan.global.error.exception.AuthException;
import com.bwmanager.jaegwan.member.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtException;
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
     * 토큰을 생성한다.
     * @param claims 토큰에 들어갈 클레임
     * @param expiration 토큰의 유효 시간 (밀리초 단위)
     * @return 토큰
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
     * 토큰이 유효한지 검증한다.
     * @param token 토큰
     * @return 토큰이 유효하면 true, 그렇지 않으면 false
     */
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true; // 유효한 경우 true 반환
        } catch (JwtException | IllegalArgumentException e) {
            return false; // 유효하지 않은 경우 false 반환
        }
    }

    /**
     * 토큰에서 클레임을 추출한다.
     * @param token 토큰
     * @return 토큰에서 추출한 클레임
     */
    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

}
