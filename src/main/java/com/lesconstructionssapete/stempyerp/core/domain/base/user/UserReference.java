package com.lesconstructionssapete.stempyerp.core.domain.base.user;

import com.lesconstructionssapete.stempyerp.core.domain.generic.GenericEntity;

/*
 * Lightweigth user reference 
 */

public class UserReference extends GenericEntity {

  private static final String USER_ENTITY_NAME = "USER";

  private final String usernameShort;

  public UserReference(
      Long userSeq,
      String userNo,
      String usernameShort,
      long createdByUserSeq) {
    super(
        USER_ENTITY_NAME,
        userNo,
        userSeq,
        createdByUserSeq);
    this.usernameShort = usernameShort;
  }

  public String getUsernameShort() {
    return usernameShort;
  }

}
