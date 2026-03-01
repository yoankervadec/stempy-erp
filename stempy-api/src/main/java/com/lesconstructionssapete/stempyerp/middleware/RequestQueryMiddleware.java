package com.lesconstructionssapete.stempyerp.middleware;

import com.lesconstructionssapete.stempyerp.http.ApiRequestContext;
import com.lesconstructionssapete.stempyerp.http.BodyKey;
import com.lesconstructionssapete.stempyerp.http.RequestMapper;
import com.lesconstructionssapete.stempyerp.http.contract.ApiRequest;
import com.lesconstructionssapete.stempyerp.http.contract.RequestQuery;

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
