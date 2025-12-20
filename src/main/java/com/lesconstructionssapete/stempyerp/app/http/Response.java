package com.lesconstructionssapete.stempyerp.app.http;

import io.javalin.http.Context;

public final class Response {

  private Response() {
    // Prevent instantiation
  }

  public static <T> void ok(Context ctx, String message, T data) {
    ctx.status(200).json(build(ctx, true, message, data));
  }

  public static <T> void created(Context ctx, String message, T data) {
    ctx.status(201).json(build(ctx, true, message, data));
  }

  public static void error(Context ctx, int status, String message) {
    ctx.status(status).json(build(ctx, false, message, null));
  }

  private static <T> ApiResponse<T> build(
      Context ctx,
      boolean success,
      String message,
      T data) {

    RequestContext reqCtx = ctx.attribute(RequestContext.class.getName());
    RequestMetadata reqMeta = (null != reqCtx) ? reqCtx.getMetadata() : null;

    String reqId = (null != reqCtx && null != reqMeta.getRequestId())
        ? reqMeta.getRequestId()
        : "unknown";

    ResponseMetadata resMeta = new ResponseMetadata(
        reqId,
        ctx.path());

    return success
        ? ApiResponse.ofSuccess(message, resMeta, data)
        : ApiResponse.ofFailure(message, resMeta);
  }

}
