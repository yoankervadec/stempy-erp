package com.lesconstructionssapete.stempyerp.user;

import java.time.Instant;

import com.lesconstructionssapete.stempyerp.generic.GenericEntity;

/*
 * Full user object
 */

public class User extends GenericEntity {

  public static final String USER_ENTITY_NAME = "USER";

  private String userName;
  private Boolean enabled;
  private Instant updatedAt;
  private Long updatedByUserId;

  public User(
      Long userId,
      String userNo,
      String userName,
      Boolean enabled,
      Instant createdAt,
      Long createdByUserId,
      Instant updatedAt,
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

  public Boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Long getUpdatedByUserId() {
    return updatedByUserId;
  }

  public void setUpdatedByUserId(Long updatedByUserId) {
    this.updatedByUserId = updatedByUserId;
  }

}
