package org.example.postservice.utiles;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    public Claims extractClaims(String token) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long extractUserId(String token) {
        Claims claims = extractClaims(token);
        Object userId = claims.get("userId"); // Извлечение поля userId
        if (userId instanceof Integer) {
            return ((Integer) userId).longValue(); // Если userId хранится как Integer
        } else if (userId instanceof Long) {
            return (Long) userId; // Если userId хранится как Long
        }
        return null;
    }
}
