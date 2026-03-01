package com.lesconstructionssapete.stempyerp.core.domain.base.auth;

import java.time.LocalDateTime;

public class UserCredential {

  private final long userCredentialId;
  private final long userId;
  private String password;
  private boolean enabled;
  private final LocalDateTime createdAt;

  public UserCredential(
      long userCredentialId,
      long userId,
      String password,
      boolean enabled,
      LocalDateTime createdAt) {
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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

}
