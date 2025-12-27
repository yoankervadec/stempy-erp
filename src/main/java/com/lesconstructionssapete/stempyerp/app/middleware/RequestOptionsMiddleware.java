package com.lesconstructionssapete.stempyerp.app.middleware;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesconstructionssapete.stempyerp.app.http.ApiRequest;
import com.lesconstructionssapete.stempyerp.app.http.ApiRequestContext;
import com.lesconstructionssapete.stempyerp.app.http.RequestOptions;
import com.lesconstructionssapete.stempyerp.core.exception.api.InvalidBodyException;
import com.lesconstructionssapete.stempyerp.core.shared.util.JsonUtil;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class RequestOptionsMiddleware implements Handler {

  private static final ObjectMapper mapper = JsonUtil.mapper();

  @Override
  public void handle(Context ctx) {

    ApiRequest req = ApiRequestContext.get(ctx);

    RequestOptions requestOptions = new RequestOptions();

    if (req != null && req.hasPayload()) {

      JsonNode root = req.getPayload();
      JsonNode optionsNode = root.get("options");

      if (optionsNode != null) {
        try {
          requestOptions = mapper.treeToValue(optionsNode, RequestOptions.class);
        } catch (IllegalArgumentException | JsonProcessingException ex) {
          throw new InvalidBodyException(null, "Invalid options format");
        }
      }
    }

    requestOptions.setIdempotencyKey(ctx.header("Idempotency-Key"));
    requestOptions.setUserAgent(ctx.header("User-Agent"));

    req.setOptions(requestOptions);

  }

}
