package com.lesconstructionssapete.stempyerp.app.http;

import io.javalin.http.Context;

public final class ApiRequestContext {

  private static final String KEY = ApiRequest.class.getName();

  public static ApiRequest get(Context ctx) {
    ApiRequest req = ctx.attribute(KEY);
    if (req == null) {
      throw new IllegalStateException("API Request not found in context");
    }
    return req;
  }

  public static void set(Context ctx, ApiRequest req) {
    ctx.attribute(KEY, req);
  }

}
