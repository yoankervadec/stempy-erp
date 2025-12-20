package com.lesconstructionssapete.stempyerp.app.http;

import io.javalin.http.Context;

public class RequestContextHolder {

  private RequestContextHolder() {
    // Prevent instantiation
  }

  public static RequestContext get(Context ctx) {
    return ctx.attribute(RequestContext.class.getName());
  }

}
