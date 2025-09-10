package com.lesconstructions.sapete.stempyerp.core.auth;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

  public static final Key key = Keys.hmacShaKeyFor(JwtConfig.SECRET.getBytes());

  public static String generateAccessToken(String userNo, String roleName) {
    return Jwts.builder()
        .setSubject(userNo)
        .claim("role", roleName)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + JwtConfig.ACCESS_TOKEN_EXPIRATION))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public static String generateRefreshToken(String userNo) {
    return Jwts.builder()
        .setSubject(userNo)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + JwtConfig.REFRESH_TOKEN_EXPIRATION))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public static String validateTokenAndGetUserNo(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }
}
