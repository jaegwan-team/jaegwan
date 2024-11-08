package com.bwmanager.jaegwan.global.util;

import com.bwmanager.jaegwan.global.error.ErrorCode;
import com.bwmanager.jaegwan.global.error.exception.AuthException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.token.secret-key}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long expiredMs;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String getMemberName(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("username", String.class);
    }

    public boolean isExpired(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    public String createJwt(String memberName, Long id) {
        Claims claims = Jwts.claims();
        claims.put("username", memberName);
        claims.put("id", id);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String[] getSubAndEmailFromIdToken(String idToken) {
        try {
            String[] parts = idToken.split("\\.");

            if (parts.length < 2) {
                throw new AuthException(ErrorCode.ID_TOKEN_FORMAT_ERROR);
            }

            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            Map<String, Object> payloadMap = objectMapper.readValue(payload, Map.class);
            String sub = (String) payloadMap.get("sub");
            String email = (String) payloadMap.get("email");

            return new String[] { sub, email };
        } catch (Exception e) {
            throw new AuthException(ErrorCode.ID_TOKEN_DECODE_FAILED);
        }
    }

}
