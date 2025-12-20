package com.lesconstructionssapete.stempyerp.app.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesconstructionssapete.stempyerp.core.shared.util.JsonUtil;

import io.javalin.http.Context;

public class RequestMapper {

  private static final ObjectMapper mapper = JsonUtil.mapper();

  public static <T> T fromBody(Context ctx, Class<T> clazz) {
    try {
      return mapper.readValue(ctx.body(), clazz);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Invalid JSON for " + clazz.getSimpleName(), e);
    }
  }

  public static <T> T fromBody(Context ctx, TypeReference<T> typeRef) {
    try {
      return mapper.readValue(ctx.body(), typeRef);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Invalid JSON for " + typeRef.getType(), e);
    }
  }

  public static <T> ApiRequest<T> fromApiRequest(Context ctx, Class<T> payloadClass) {
    try {
      return mapper.readValue(
          ctx.body(),
          mapper.getTypeFactory()
              .constructParametricType(ApiRequest.class, payloadClass));
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to parse ApiRequest<" + payloadClass.getSimpleName() + ">", e);
    }
  }

  public static ApiRequest<?> fromApiRequest(Context ctx) {
    try {
      return mapper.readValue(ctx.body(), ApiRequest.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Invalid ApiRequest payload", e);
    }
  }

  public static <T> T convertPayload(Object payload, Class<T> clazz) {
    return mapper.convertValue(payload, clazz);
  }

}
