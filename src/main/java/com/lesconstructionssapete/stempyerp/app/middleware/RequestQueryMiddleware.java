package com.lesconstructionssapete.stempyerp.app.middleware;

import com.lesconstructionssapete.stempyerp.app.http.ApiRequestContext;
import com.lesconstructionssapete.stempyerp.app.http.BodyKey;
import com.lesconstructionssapete.stempyerp.app.http.RequestMapper;
import com.lesconstructionssapete.stempyerp.app.http.contract.ApiRequest;
import com.lesconstructionssapete.stempyerp.app.http.contract.RequestQuery;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class RequestQueryMiddleware implements Handler {

  @Override
  public void handle(Context ctx) {

    ApiRequest req = ApiRequestContext.get(ctx);

    if (req != null) {
      RequestQuery requestQuery = null;

      if (req.hasBody()) {
        requestQuery = RequestMapper.map(req.getBody(), RequestQuery.class, BodyKey.QUERY);
      }

      req.setQuery(requestQuery);
    }

  }
}
