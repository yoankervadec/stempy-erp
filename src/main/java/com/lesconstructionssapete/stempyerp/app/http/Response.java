package com.lesconstructionssapete.stempyerp.app.http;

import com.lesconstructionssapete.stempyerp.app.http.contract.ApiRequest;
import com.lesconstructionssapete.stempyerp.app.http.contract.ApiResponse;
import com.lesconstructionssapete.stempyerp.app.http.contract.ResponseMetadata;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public final class Response {

  private Response() {
    // Prevent instantiation
  }

  public static <T> void ok(Context ctx, String message, T data) {
    send(ctx, HttpStatus.OK, true, message, data);
  }

  public static <T> void created(Context ctx, String message, T data) {
    send(ctx, HttpStatus.CREATED, true, message, data);
  }

  public static void error(Context ctx, HttpStatus status, String message) {
    send(ctx, status, false, message, null);
  }

  private static <T> void send(
      Context ctx,
      HttpStatus status,
      boolean success,
      String message,
      T data) {

    ApiRequest req = ApiRequestContext.get(ctx);

    applyHeaders(ctx, req);

    ApiResponse<T> res = build(ctx, req, success, message, data);

    ctx.status(status).json(res);

  }

  private static <T> ApiResponse<T> build(
      Context ctx,
      ApiRequest req,
      boolean success,
      String message,
      T data) {

    var context = req != null ? req.getContext() : null;

    var metadata = new ResponseMetadata(
        context != null ? context.getRequestId() : null,
        ctx.path());

    return success
        ? ApiResponse.ofSuccess(message, metadata, null, null, null, data)
        : ApiResponse.ofFailure(message, metadata);
  }

  // Helper method to set headers from ApiRequest options
  private static void applyHeaders(Context ctx, ApiRequest req) {

    if (req == null || req.getOptions() == null) {
      return;
    }

    String idempotencyKey = req.getOptions().getIdempotencyKey();
    if (idempotencyKey != null) {
      ctx.header("Idempotency-Key", idempotencyKey);
    }
  }

}
