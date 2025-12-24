package com.lesconstructionssapete.stempyerp.app.middleware;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesconstructionssapete.stempyerp.app.http.ApiRequest;
import com.lesconstructionssapete.stempyerp.app.http.ServerContext;
import com.lesconstructionssapete.stempyerp.core.exception.api.InvalidBodyException;
import com.lesconstructionssapete.stempyerp.core.shared.util.JsonUtil;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ApiRequestMiddleware implements Handler {

  private static final ObjectMapper mapper = JsonUtil.mapper();

  @Override
  public void handle(Context ctx) throws JsonMappingException, JsonProcessingException {

    ServerContext serverContext = ctx.attribute(ServerContext.class.getName());

    JsonNode payload = null;

    String contentType = ctx.contentType();

    if ((contentType != null &&
        contentType.toLowerCase().contains("application/json")) &&
        ctx.body().length() > 0) {
      try {
        payload = mapper.readTree(ctx.body());
      } catch (JsonProcessingException e) {
        throw new InvalidBodyException(null, null);
      }
    }

    ApiRequest apiRequest = new ApiRequest(serverContext, payload);

    ctx.attribute(ApiRequest.class.getName(), apiRequest);
  }

}
