package com.lesconstructionssapete.stempyerp.core.domain.base.auth;

import java.time.LocalDateTime;

public class AuthToken {

  private Long refreshTokenSeq;
  private Long userSeq;
  private String userNo;
  private String token;
  private String refreshToken;
  private LocalDateTime expiresAt;
  private LocalDateTime createdAt;

  public AuthToken(Long refreshTokenSeq, Long userSeq, String userNo, String token, String refreshToken,
      LocalDateTime expiresAt, LocalDateTime createdAt) {
    this.refreshTokenSeq = refreshTokenSeq;
    this.userSeq = userSeq;
    this.userNo = userNo;
    this.token = token;
    this.refreshToken = refreshToken;
    this.expiresAt = expiresAt;
    this.createdAt = createdAt;
  }

  public long getRefreshTokenSeq() {
    return refreshTokenSeq;
  }

  public void setRefreshTokenSeq(long refreshTokenSeq) {
    this.refreshTokenSeq = refreshTokenSeq;
  }

  public long getUserSeq() {
    return userSeq;
  }

  public void setUserSeq(long userSeq) {
    this.userSeq = userSeq;
  }

  public String getUserNo() {
    return userNo;
  }

  public void setUserNo(String userNo) {
    this.userNo = userNo;
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

  public LocalDateTime getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(LocalDateTime expiresAt) {
    this.expiresAt = expiresAt;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

}
