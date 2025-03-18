package com.toilamdev.stepbystep.utils;

import com.toilamdev.stepbystep.entity.User;
import com.toilamdev.stepbystep.exception.GlobalException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTokenUtils {
    @Value("${jwt.expiration:3600}")
    private int expiration;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @PostConstruct
    public void validateConfig() {
        if (this.secretKey == null || this.secretKey.isBlank()) {
            throw new IllegalStateException("JWT secret key is missing!");
        }
        if (this.expiration <= 0) {
            log.warn("JWT expiration is not properly set, using default: 3600 seconds");
            this.expiration = 3600;
        }
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getEmail())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + this.expiration * 1000L))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Dùng HS256
                    .compact();
        } catch (Exception e) {
            log.error("Generate Token Fail", e);
            throw new GlobalException.JwtGenerationException("Khởi tạo Access Token thất bại");
        }
    }

    private Key getSignInKey() {
        byte[] bytesKey = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(bytesKey);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> function) {
        Claims claims = this.extractAllClaims(token);
        return function.apply(claims);
    }

    public boolean isTokenExpiration(String token) {
        try {
            Date expirationDate = this.extractClaim(token, Claims::getExpiration);
            return expirationDate.before(new Date());
        } catch (Exception e) {
            log.error("Failed to check token expiration", e);
            return true;
        }
    }
}
