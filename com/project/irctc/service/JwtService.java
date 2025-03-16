package com.project.irctc.service;



import com.project.irctc.exception.InvalidTokenException;
import com.project.irctc.exception.TokenExpiredException;
import com.project.irctc.repository.JwtTokenRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.token.expiration}")
    private long expirationTime;

    private final JwtTokenRepository jwtTokenRepository;

    public JwtService(JwtTokenRepository jwtTokenRepository) {
        this.jwtTokenRepository = jwtTokenRepository;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
//        List<String> roles = Arrays.stream(userDetails.getAuthorities().toArray())
//                .map(grantedAuthority -> grantedAuthority.toString()) // Convert to role strings
//                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role) // Add "ROLE_" prefix
//                .collect(Collectors.toList());
        return createToken(new HashMap<>(), userDetails.getUsername());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        if (isTokenExpired(token)) {
            throw new TokenExpiredException("Token has expired.");
        }
        if (!isTokenValid(token)) {
            throw new InvalidTokenException("Token is invalid.");
        }
        if (isTokenSignatureInvalid(token)) {
            throw new InvalidTokenException("Token signature is invalid.");
        }
        return extractUsername(token).equals(userDetails.getUsername());
    }

    public boolean isTokenValid(String token) {
        return jwtTokenRepository.existsByToken(token);
    }

    public boolean isTokenSignatureInvalid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return false;
        } catch (SignatureException e) {
            return true;
        }
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("Token has expired.");
        } catch (MalformedJwtException e) {
            throw new InvalidTokenException("Token is malformed.");
        } catch (SignatureException e) {
            throw new InvalidTokenException("Invalid token signature.");
        } catch (Exception e) {
            throw new InvalidTokenException("Error while parsing token.");
        }
    }
}
