package com.lesconstructionssapete.stempyerp.core.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public final class JwtUtil {

  private static final Key KEY = Keys.hmacShaKeyFor(JwtConfig.SECRET.getBytes(StandardCharsets.UTF_8));

  private JwtUtil() {
  }

  public static String generateAccessToken(String userNo, String roleName) {
    return Jwts.builder()
        .setSubject(userNo)
        .claim("type", "access")
        .claim("role", roleName)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + JwtConfig.ACCESS_TOKEN_EXPIRATION))
        .signWith(KEY, SignatureAlgorithm.HS256)
        .compact();
  }

  public static String generateRefreshToken(String userNo) {
    return Jwts.builder()
        .setSubject(userNo)
        .claim("type", "refresh")
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + JwtConfig.REFRESH_TOKEN_EXPIRATION))
        .signWith(KEY, SignatureAlgorithm.HS256)
        .compact();
  }

  public static String validateAccessTokenAndGetUserNo(String token) {
    var claims = Jwts.parserBuilder()
        .setSigningKey(KEY)
        .build()
        .parseClaimsJws(token)
        .getBody();

    if (!"access".equals(claims.get("type"))) {
      throw new JwtException("Invalid token type");
    }

    return claims.getSubject();
  }

  public static String validateRefreshTokenAndGetUserNo(String token) {
    var claims = Jwts.parserBuilder()
        .setSigningKey(KEY)
        .build()
        .parseClaimsJws(token)
        .getBody();

    if (!"refresh".equals(claims.get("type"))) {
      throw new JwtException("Invalid token type");
    }

    return claims.getSubject();
  }
}