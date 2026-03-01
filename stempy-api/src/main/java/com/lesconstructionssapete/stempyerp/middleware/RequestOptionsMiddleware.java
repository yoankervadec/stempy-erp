package com.lesconstructionssapete.stempyerp.middleware;

import com.lesconstructionssapete.stempyerp.http.ApiRequestContext;
import com.lesconstructionssapete.stempyerp.http.BodyKey;
import com.lesconstructionssapete.stempyerp.http.RequestMapper;
import com.lesconstructionssapete.stempyerp.http.contract.ApiRequest;
import com.lesconstructionssapete.stempyerp.http.contract.RequestOptions;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class RequestOptionsMiddleware implements Handler {

  @Override
  public void handle(Context ctx) {

    ApiRequest req = ApiRequestContext.get(ctx);

    if (req != null) {
      RequestOptions requestOptions = null;

      if (req.hasBody()) {
        requestOptions = RequestMapper.map(req.getBody(), RequestOptions.class, BodyKey.OPTIONS);
      }

      // If no options provided in body, create an empty one to hold headers
      if (requestOptions == null) {
        requestOptions = new RequestOptions();
      }

      requestOptions.setIdempotencyKey(ctx.header("Idempotency-Key"));
      requestOptions.setUserAgent(ctx.header("User-Agent"));

      req.setOptions(requestOptions);
    }

  }
}
