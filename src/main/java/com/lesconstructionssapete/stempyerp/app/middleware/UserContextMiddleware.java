package com.lesconstructionssapete.stempyerp.app.middleware;

import com.lesconstructionssapete.stempyerp.app.facade.base.user.UserFacade;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;
import com.lesconstructionssapete.stempyerp.core.exception.api.UnauthorizedException;

public class UserContextMiddleware {

  private final UserFacade userFacade;

  public UserContextMiddleware(UserFacade userFacade) {
    this.userFacade = userFacade;
  }

  public User resolve(String userNo) {
    if (userNo == null) {
      throw new UnauthorizedException(null, null);
    }

    User user = userFacade.findByUserNo(userNo);
    if (user == null) {
      throw new UnauthorizedException(null, null);
    }

    return user;
  }

}
