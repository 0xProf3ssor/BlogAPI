package com.zenith.blog.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    //Generate Token
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
    String generateToken(UserDetails userDetails);

    //Check Token Valid Or Not
    boolean isTokenValid(String token, UserDetails userDetails);
    boolean isTokenExpired(String token);

    //Extract Email
    String extractEmail(String token);

    //Extract Expiration
    Date extractExpiration(String token);

    //Extract All Claims
    Claims extractAllClaims(String token);

    //Extract Claim
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
}
