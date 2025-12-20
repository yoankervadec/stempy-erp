package com.lesconstructionssapete.stempyerp.app.middleware;

import com.lesconstructionssapete.stempyerp.app.http.ApiRequest;
import com.lesconstructionssapete.stempyerp.app.http.RequestContext;
import com.lesconstructionssapete.stempyerp.app.http.RequestContextHolder;
import com.lesconstructionssapete.stempyerp.app.http.RequestMapper;

import io.javalin.http.Context;

public class ApiRequestMiddleware {

  public static void build(Context ctx) {

    if (ctx.body().isBlank()) {
      return;
    }

    RequestContext rc = RequestContextHolder.get(ctx);

    ApiRequest<?> request = RequestMapper.fromApiRequest(ctx);
    request.setMetadata(rc.getMetadata());

    rc.setApiRequest(request);

  }

}
