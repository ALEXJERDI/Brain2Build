package com.example.brain2build.config;

import com.example.brain2build.domain.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtProvider {

    @Value("${application.security.jwt.secret}")
    private String jwtSecret;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // ✅ Generate token based on userId (not email)
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());        // optional, for UI display
        claims.put("roles", user.getRoles());        // optional, can filter later

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(user.getId())) // main subject = ID
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Extract userId from token (instead of email)
    public Long extractUserId(String token) {
        String subject = extractClaim(token, Claims::getSubject);
        return Long.parseLong(subject);
    }

    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("email", String.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // ✅ Public token validation method
    public boolean validateToken(String token, UserDetails userDetails) {
        Long tokenUserId = extractUserId(token);

        // If you have a User entity implementing UserDetails:
        Long dbUserId = null;
        if (userDetails instanceof com.example.brain2build.domain.entity.User user) {
            dbUserId = user.getId();
        }

        return (dbUserId != null && tokenUserId.equals(dbUserId) && !isTokenExpired(token));
    }

    // keep the helper private
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


}
