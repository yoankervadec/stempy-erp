package com.lesconstructionssapete.stempyerp.middleware;

import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.User;
import com.lesconstructionssapete.stempyerp.domain.shared.query.ComparisonOperator;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.shared.query.FilterCondition;
import com.lesconstructionssapete.stempyerp.exception.AuthenticationException;
import com.lesconstructionssapete.stempyerp.exception.UserNotFoundException;
import com.lesconstructionssapete.stempyerp.facade.auth.UserFacade;
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

    DomainQuery userQuery = new DomainQuery(
        new FilterCondition(
            "userNo",
            ComparisonOperator.EQUALS,
            userNo),
        null,
        null);

    List<User> users = userFacade.fetch(userQuery);

    if (users.isEmpty()) {
      throw new UserNotFoundException("Authenticated user not found in the system");
    }

    User user = users.get(0);
    req.setContextUser(user);

    // Further authorization logic will be added later

  }

}
