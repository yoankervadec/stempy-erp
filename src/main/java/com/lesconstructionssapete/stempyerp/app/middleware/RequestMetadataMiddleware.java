package com.lesconstructionssapete.stempyerp.app.middleware;

import java.time.Instant;
import java.util.UUID;

import com.lesconstructionssapete.stempyerp.app.http.RequestMetadata;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;

import io.javalin.http.Context;

public class RequestMetadataMiddleware {

  public RequestMetadata build(Context ctx, User user) {
    RequestMetadata metadata = new RequestMetadata();

    metadata.setRequestId(UUID.randomUUID().toString());
    metadata.setTimestamp(Instant.now());

    metadata.setUserNo(user != null ? user.getEntityNo() : null);
    metadata.setUser(user);
    metadata.setClientIp(ctx.ip());
    metadata.setSource(ctx.header("X-Source"));

    metadata.setIdempotencyKey(ctx.header("Idempotency-Key"));

    return metadata;
  }

}
