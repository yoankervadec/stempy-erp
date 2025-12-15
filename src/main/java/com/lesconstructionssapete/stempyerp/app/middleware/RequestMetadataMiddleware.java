package com.lesconstructionssapete.stempyerp.app.middleware;

import java.time.Instant;
import java.util.UUID;

import com.lesconstructionssapete.stempyerp.app.http.RequestMetadata;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class RequestMetadataMiddleware implements Handler {

  @Override
  public void handle(Context ctx) {

    RequestMetadata metadata = new RequestMetadata();

    metadata.setRequestId(UUID.randomUUID().toString());
    metadata.setTimestamp(Instant.now());

    metadata.setUserNo(ctx.attribute("userNo"));
    metadata.setUser(ctx.attribute("user"));

    metadata.setIdempotencyKey(ctx.header("Idempotency-Key"));

    ctx.attribute("requestMetadata", metadata);
  }

}
