package com.lesconstructionssapete.stempyerp.app.middleware;

import java.time.Instant;
import java.util.UUID;

import com.lesconstructionssapete.stempyerp.app.http.ServerContext;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ServerContextMiddleware implements Handler {

  @Override
  public void handle(Context ctx) {

    // metadata.setIdempotencyKey(ctx.header("Idempotency-Key"));

    var serverContext = new ServerContext(
        UUID.randomUUID().toString(),
        Instant.now(),
        ctx.attribute("AUTHENTICATED_USER_NO"),
        ctx.ip(),
        ctx.header("X-Source"));

    ctx.attribute(ServerContext.class.getName(), serverContext);
  }

}
