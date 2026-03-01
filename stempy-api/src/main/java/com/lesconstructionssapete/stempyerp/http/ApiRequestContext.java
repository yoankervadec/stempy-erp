package com.lesconstructionssapete.stempyerp.http;

import com.lesconstructionssapete.stempyerp.http.contract.ApiRequest;

import io.javalin.http.Context;

public final class ApiRequestContext {

  private static final String KEY = ApiRequest.class.getName();

  public static ApiRequest get(Context ctx) {
    ApiRequest req = ctx.attribute(KEY);
    if (req == null) {
      set(ctx, new ApiRequest());
    }
    return req;
  }

  public static void set(Context ctx, ApiRequest req) {
    ctx.attribute(KEY, req);
  }

}
