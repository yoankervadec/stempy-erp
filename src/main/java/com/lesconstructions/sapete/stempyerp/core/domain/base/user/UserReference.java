package com.lesconstructions.sapete.stempyerp.core.domain.base.user;

import com.lesconstructions.sapete.stempyerp.core.domain.base.GenericEntity;
import com.lesconstructions.sapete.stempyerp.core.shared.constant.ConstantCache;
import com.lesconstructions.sapete.stempyerp.core.shared.constant.ConstantUtil;

/*
 * Lightweigth user reference 
 */

public class UserReference extends GenericEntity {

  private static final String USER_ENTITY_NAME = "USER";

  private final String usernameShort;

  public UserReference(
      Long sequenceNo,
      String usernameShort,
      Long createdByUserSeq) {
    super(
        ConstantUtil.findByName(ConstantCache.getEntityTypes(), USER_ENTITY_NAME),
        sequenceNo,
        createdByUserSeq);
    this.usernameShort = usernameShort;
  }

  public String getUsernameShort() {
    return usernameShort;
  }

}
