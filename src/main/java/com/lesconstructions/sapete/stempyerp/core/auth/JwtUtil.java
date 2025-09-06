package com.lesconstructions.sapete.stempyerp.core.auth;

import java.security.Key;
import java.util.Date;

import com.lesconstructions.sapete.stempyerp.core.domain.base.constant.UserRole;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

  public static final Key key = Keys.hmacShaKeyFor(JwtConfig.SECRET.getBytes());

  public static String generateAccessToken(String usernameLong, UserRole role) {
    return Jwts.builder()
        .setSubject(usernameLong)
        .claim("role", role.getName())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + JwtConfig.ACCESS_TOKEN_EXPIRATION))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public static String generateRefreshToken(String usernameLong) {
    return Jwts.builder()
        .setSubject(usernameLong)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + JwtConfig.REFRESH_TOKEN_EXPIRATION))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public static String validateTokenAndGetUserId(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }
}
