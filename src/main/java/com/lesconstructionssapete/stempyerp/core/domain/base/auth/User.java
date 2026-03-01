package com.lesconstructionssapete.stempyerp.core.domain.base.auth;

import java.time.LocalDateTime;

import com.lesconstructionssapete.stempyerp.core.domain.generic.GenericEntity;

/*
 * Full user object
 */

public class User extends GenericEntity {

  public static final String USER_ENTITY_NAME = "USER";

  private String userName;
  private Boolean enabled;
  private LocalDateTime updatedAt;
  private Long updatedByUserId;

  public User(
      Long userId,
      String userNo,
      String userName,
      Boolean enabled,
      LocalDateTime createdAt,
      Long createdByUserId,
      LocalDateTime updatedAt,
      Long updatedByUserId) {
    super(
        USER_ENTITY_NAME,
        userNo,
        userId,
        createdAt,
        createdByUserId);
    this.enabled = enabled;
    this.userName = userName;
    this.updatedAt = updatedAt;
    this.updatedByUserId = updatedByUserId;
  }

  public static String getUserEntityName() {
    return USER_ENTITY_NAME;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Long getUpdatedByUserId() {
    return updatedByUserId;
  }

  public void setUpdatedByUserId(Long updatedByUserId) {
    this.updatedByUserId = updatedByUserId;
  }

}
