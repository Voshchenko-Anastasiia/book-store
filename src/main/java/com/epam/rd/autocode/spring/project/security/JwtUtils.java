package com.epam.rd.autocode.spring.project.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    public String generateToken(UserDetails userDetails) {
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        logger.debug("Generating JWT token for user: {}", userDetails.getUsername());

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SignatureException e) {
            logger.error("JWT ERROR: Invalid signature - {}", e.getMessage());
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            logger.error("JWT ERROR: Malformed token - {}", e.getMessage());
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            logger.warn("JWT WARN: Token expired - {}", e.getMessage());
        } catch (io.jsonwebtoken.UnsupportedJwtException e) {
            logger.error("JWT ERROR: Unsupported token - {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT ERROR: Empty claims - {}", e.getMessage());
        }
        return false;
    }
}