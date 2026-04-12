package com.lesconstructionssapete.stempyerp.infrastructure.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import com.lesconstructionssapete.stempyerp.security.TokenProvider;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public final class JwtTokenProvider implements TokenProvider {

  private final Key key;
  private final long accessExpiration;
  private final long refreshExpiration;

  public JwtTokenProvider(
      String secret,
      long accessExpiration,
      long refreshExpiration) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.accessExpiration = accessExpiration;
    this.refreshExpiration = refreshExpiration;
  }

  @Override
  public String generateAccessToken(String userNo) {
    return Jwts.builder()
        .setSubject(userNo)
        .claim("type", "access")
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  @Override
  public String generateRefreshToken(String userNo) {
    return Jwts.builder()
        .setSubject(userNo)
        .claim("type", "refresh")
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  @Override
  public String validateAccessTokenAndGetUserNo(String token) {
    var claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();

    if (!"access".equals(claims.get("type"))) {
      throw new JwtException("Invalid token type");
    }

    return claims.getSubject();
  }

  @Override
  public String validateRefreshTokenAndGetUserNo(String token) {
    var claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();

    if (!"refresh".equals(claims.get("type"))) {
      throw new JwtException("Invalid token type");
    }

    return claims.getSubject();
  }

  @Override
  public long getRefreshTokenExpirationMillis() {
    return refreshExpiration;
  }
}