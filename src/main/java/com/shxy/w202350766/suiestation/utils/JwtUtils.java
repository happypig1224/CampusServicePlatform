package com.shxy.w202350766.suiestation.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

/**
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime
 */
public class JwtUtils {
    // 使用固定的密钥，确保服务器重启后仍能解析之前的token
    private static final String SECRET_STRING = "suiestation2023secretkeyforjwttoken123456789";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(
            Base64.getEncoder().encodeToString(SECRET_STRING.getBytes())
    ));
    
    /**
     * 生成token
     * @param userId 用户id
     * @return token
     */
    public static String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .signWith(SECRET_KEY)
                .compact();

    }
    
    /**
     * 解析token
     * @param token JWT token
     * @return 用户ID
     */
    public static Long parseToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            return null;
        }
    }

    public static void clearToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);
    }
}