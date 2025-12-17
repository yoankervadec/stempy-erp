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

  // Convenience overload for ApiRequest<T>
  public static <T> ApiRequest<T> fromApiRequest(Context ctx, Class<T> payloadClass) {
    try {
      TypeReference<ApiRequest<T>> typeRef = new TypeReference<>() {
      };
      // Using intermediate string conversion to bind nested payload properly
      ApiRequest<?> temp = mapper.readValue(ctx.body(), ApiRequest.class);
      String payloadJson = mapper.writeValueAsString(temp.getPayload());
      T payload = mapper.readValue(payloadJson, payloadClass);
      ApiRequest<T> finalReq = new ApiRequest<>();
      finalReq.setMetadata(temp.getMetadata());
      finalReq.setPayload(payload);
      return finalReq;
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to parse ApiRequest<" + payloadClass.getSimpleName() + ">", e);
    }
  }

}
