package com.lesconstructionssapete.stempyerp.core.domain.base.auth;

import java.time.LocalDateTime;

public class AuthToken {

  private Long id;
  private Long userId;
  private String token;
  private String refreshToken;
  private LocalDateTime refreshTokenExpiresAt;
  private LocalDateTime createdAt;

  public AuthToken(
      Long id,
      Long userId,
      String token,
      String refreshToken,
      LocalDateTime refreshTokenExpiresAt,
      LocalDateTime createdAt) {
    this.id = id;
    this.userId = userId;
    this.token = token;
    this.refreshToken = refreshToken;
    this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    this.createdAt = createdAt;
  }

  public long getId() {
    return id;
  }

  public void setRefreshTokenSeq(long refreshTokenSeq) {
    this.id = refreshTokenSeq;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserSeq(long userSeq) {
    this.userId = userSeq;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public LocalDateTime getRefreshTokenExpiresAt() {
    return refreshTokenExpiresAt;
  }

  public void setRefreshTokenExpiresAt(LocalDateTime expiresAt) {
    this.refreshTokenExpiresAt = expiresAt;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

}
