package com.lesconstructionssapete.stempyerp.domain.auth;

import java.time.Instant;

public class UserCredential {

  private final long userCredentialId;
  private final long userId;
  private String userNo;
  private String password;
  private boolean enabled;
  private final Instant createdAt;

  public UserCredential(
      long userCredentialId,
      long userId,
      String password,
      boolean enabled,
      Instant createdAt) {
    this.userCredentialId = userCredentialId;
    this.userId = userId;
    this.password = password;
    this.enabled = enabled;
    this.createdAt = createdAt;
  }

  public long getUserCredentialId() {
    return userCredentialId;
  }

  public long getUserId() {
    return userId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  public String getUserNo() {
    return userNo;
  }

  public void setUserNo(String userNo) {
    this.userNo = userNo;
  }

}
