package com.lesconstructionssapete.stempyerp.app.middleware;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesconstructionssapete.stempyerp.app.http.ApiRequest;
import com.lesconstructionssapete.stempyerp.app.http.RequestMetadata;
import com.lesconstructionssapete.stempyerp.core.shared.util.JsonUtil;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ApiRequestMiddleware implements Handler {

  private static final ObjectMapper mapper = JsonUtil.mapper();

  @Override
  public void handle(Context ctx) throws JsonMappingException, JsonProcessingException {

    RequestMetadata metadata = ctx.attribute("REQUEST_METADATA");

    JsonNode payload = null;

    if (ctx.body().length() > 0) {
      payload = mapper.readTree(ctx.body());
    }

    ApiRequest apiRequest = new ApiRequest(metadata, null, payload);

    ctx.attribute("API_REQUEST", apiRequest);
  }

}
