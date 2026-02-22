package com.lesconstructionssapete.stempyerp.app.middleware;

import com.lesconstructionssapete.stempyerp.app.http.ApiRequestContext;
import com.lesconstructionssapete.stempyerp.app.http.BodyKey;
import com.lesconstructionssapete.stempyerp.app.http.RequestMapper;
import com.lesconstructionssapete.stempyerp.app.http.contract.ApiRequest;
import com.lesconstructionssapete.stempyerp.app.http.contract.RequestOptions;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class RequestOptionsMiddleware implements Handler {

  @Override
  public void handle(Context ctx) {

    ApiRequest req = ApiRequestContext.get(ctx);

    // RequestOptions requestOptions = null;

    // if (req != null && req.hasBody()) {
    // requestOptions = RequestMapper.map(req.getBody(), RequestOptions.class,
    // BodyKey.OPTIONS);
    // }

    // requestOptions.setIdempotencyKey(ctx.header("Idempotency-Key"));
    // requestOptions.setUserAgent(ctx.header("User-Agent"));

    // if (null != req) {
    // req.setOptions(requestOptions);
    // }

    if (req != null) {
      RequestOptions requestOptions;

      if (req.hasBody()) {
        requestOptions = RequestMapper.map(req.getBody(), RequestOptions.class, BodyKey.OPTIONS);
      } else {
        requestOptions = new RequestOptions();
      }

      requestOptions.setIdempotencyKey(ctx.header("Idempotency-Key"));
      requestOptions.setUserAgent(ctx.header("User-Agent"));

      req.setOptions(requestOptions);
    }

  }
}
