package com.sideproject.shoppingcart.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "XnDQkuZz8SdGGSEfKCV9BB3Xfes8wthMNLtdGlVXUKg"; // 至少 32 个字符
    private static final long EXPIRATION_TIME = 86400000; // 1 天有效期

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 產生 JWT Token
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), io.jsonwebtoken.SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 解析 Email
     */
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)  // 0.10.8 版本的 `setSigningKey`
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }


    /**
     * 驗證 Token
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY) // ✅ JJWT 0.10.8 正确用法
                    .parseClaimsJws(token); // 解析 JWT，验证签名
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
