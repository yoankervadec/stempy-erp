package com.lesconstructionssapete.stempyerp.app.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesconstructionssapete.stempyerp.core.shared.util.JsonUtil;

public final class ApiRequestMapper {

  private static final ObjectMapper mapper = JsonUtil.mapper();

  public static <T> TypedApiRequest<T> map(ApiRequest request, Class<T> type) {
    if (!request.hasPayload()) {
      // No payload present...
      // TODO : throw exception
      return new TypedApiRequest<>(request, null);
    }

    JsonNode root = request.getPayload();
    JsonNode payloadNode = root.get("payload");

    try {
      T body = mapper.treeToValue(payloadNode, type);
      return new TypedApiRequest<>(request, body);
    } catch (IllegalArgumentException | JsonProcessingException e) {
      throw new RuntimeException("Failed to map API request", e);
    }
  }

}
