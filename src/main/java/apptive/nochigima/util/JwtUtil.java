package apptive.nochigima.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import apptive.nochigima.exception.UnauthorizedException;

@Slf4j
@Component
public class JwtUtil {

    private static final long ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24; // 1일
    private static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 30; // 30일

    private final SecretKey secretKey;

    public JwtUtil(@Value("${auth.jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(Long userId) {
        return createToken(userId, ACCESS_TOKEN_EXPIRATION);
    }

    public String createRefreshToken(Long userId) {
        return createToken(userId, REFRESH_TOKEN_EXPIRATION);
    }

    private String createToken(Long userId, long expiration) {
        Date now = new Date();

        return Jwts.builder()
                .claim("userId", userId)
                .expiration(new Date(now.getTime() + expiration))
                .issuedAt(now)
                .signWith(secretKey)
                .compact();
    }

    public void validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("JWT 토큰이 만료됨");
        } catch (RuntimeException e) {
            throw new UnauthorizedException("JWT 토큰이 유효하지 않음");
        }
    }

    public Long getUserId(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("userId", Long.class);
    }
}
