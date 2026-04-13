package com.xiaoyuan.schoolcircle.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getKey() {
        // 调试打印
        System.out.println("=== 读取到的 secret 内容：" + secret);
        System.out.println("=== secret 长度：" + (secret == null ? "null" : secret.length()));
        if (secret == null || secret.length() < 32) {
            throw new RuntimeException("JWT 密钥无效，长度不足 32 位或未配置");
        }
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(Integer userId, String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000L);
        return Jwts.builder()
                .subject(userId.toString())
                .claim("username", username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getKey())
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Integer getUserId(String token) {
        return Integer.valueOf(parseToken(token).getSubject());
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}