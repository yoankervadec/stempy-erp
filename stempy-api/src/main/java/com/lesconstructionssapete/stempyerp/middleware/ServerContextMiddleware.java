package com.lesconstructionssapete.stempyerp.middleware;

import java.time.Instant;
import java.util.UUID;

import com.lesconstructionssapete.stempyerp.http.ApiRequestContext;
import com.lesconstructionssapete.stempyerp.http.contract.ApiRequest;
import com.lesconstructionssapete.stempyerp.http.contract.RequestContext;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ServerContextMiddleware implements Handler {

  @Override
  public void handle(Context ctx) {

    ApiRequest req = ApiRequestContext.get(ctx);

    // Full User is fetched in AuthorizationMiddleware

    var serverContext = new RequestContext(
        UUID.randomUUID().toString(),
        Instant.now(),
        ctx.attribute("AUTHENTICATED_USER_NO"),
        ctx.ip());

    if (req != null) {
      req.setContext(serverContext);
    }
  }

}
