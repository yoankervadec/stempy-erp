package com.lesconstructionssapete.stempyerp.app.middleware;

import java.time.Instant;
import java.util.UUID;

import com.lesconstructionssapete.stempyerp.app.http.RequestMetadata;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class RequestMetadataBuilderMiddleware implements Handler {

  @Override
  public void handle(Context ctx) {

    RequestMetadata metadata = new RequestMetadata();

    metadata.setRequestId(UUID.randomUUID().toString());
    metadata.setTimestamp(Instant.now());

    metadata.setUserNo(ctx.attribute("AUTHENTICATED_USER_NO"));
    metadata.setClientIp(ctx.ip());
    metadata.setSource(ctx.header("X-Source"));

    metadata.setIdempotencyKey(ctx.header("Idempotency-Key"));

    ctx.attribute("REQUEST_METADATA", metadata);
  }

}
