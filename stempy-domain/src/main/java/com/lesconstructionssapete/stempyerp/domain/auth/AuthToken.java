package com.lesconstructionssapete.stempyerp.domain.auth;

import java.time.Instant;

public class AuthToken {

  private Long id;
  private Long userId;
  private String token;
  private String refreshToken;
  private Instant refreshTokenExpiresAt;
  private boolean enabled;
  private Instant createdAt;

  public AuthToken(
      Long id,
      Long userId,
      String token,
      String refreshToken,
      Instant refreshTokenExpiresAt,
      boolean enabled,
      Instant createdAt) {
    this.id = id;
    this.userId = userId;
    this.token = token;
    this.refreshToken = refreshToken;
    this.enabled = enabled;
    this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    this.createdAt = createdAt;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setRefreshTokenSeq(long refreshTokenSeq) {
    this.id = refreshTokenSeq;
  }

  public Long getUserId() {
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

  public Instant getRefreshTokenExpiresAt() {
    return refreshTokenExpiresAt;
  }

  public void setRefreshTokenExpiresAt(Instant expiresAt) {
    this.refreshTokenExpiresAt = expiresAt;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

}
