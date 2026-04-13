package com.lesconstructionssapete.stempyerp.middleware;

import java.util.List;

import com.lesconstructionssapete.stempyerp.context.RequestContext;
import com.lesconstructionssapete.stempyerp.domain.exception.UserNotFoundException;
import com.lesconstructionssapete.stempyerp.domain.field.user.UserField;
import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.user.User;
import com.lesconstructionssapete.stempyerp.exception.AuthenticationException;
import com.lesconstructionssapete.stempyerp.facade.spi.user.UserFacade;
import com.lesconstructionssapete.stempyerp.http.ApiRequestContext;
import com.lesconstructionssapete.stempyerp.http.contract.ApiRequest;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class AuthorizationMiddleware implements Handler {

  private final UserFacade userFacade;

  public AuthorizationMiddleware(UserFacade userFacade) {
    this.userFacade = userFacade;
  }

  @Override
  public void handle(Context ctx) {

    ApiRequest req = ApiRequestContext.get(ctx);

    String userNo = req.getContext().getUserNo();
    if (userNo == null) {
      throw new AuthenticationException(null, "No authenticated user found in the request context");
    }

    DomainQuery q = DomainQuery.builder()
        .where(w -> w.and(
            c -> c.equals(UserField.USER_NO, userNo)))
        .build();

    List<User> users = userFacade.fetch(q);

    if (users.isEmpty()) {
      throw new UserNotFoundException("Authenticated user not found in the system");
    }

    User user = users.get(0);
    req.setContextUser(user);
    RequestContext.setUserId(user.getEntityId());

  }

}
