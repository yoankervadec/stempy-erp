package com.lesconstructionssapete.stempyerp.app.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesconstructionssapete.stempyerp.core.exception.api.InvalidBodyException;
import com.lesconstructionssapete.stempyerp.core.shared.util.JsonUtil;

public final class RequestMapper {

  private RequestMapper() {
    // Static class
  }

  private static final ObjectMapper MAPPER = JsonUtil.mapper();

  public static <T> T map(JsonNode json, Class<T> type, BodyKey bodyPart) {
    if (json == null || json.isNull()) {
      return null;
    }

    JsonNode targetNode = json.get(bodyPart.getKey());
    if (targetNode == null || targetNode.isNull()) {
      return null;
    }

    try {
      return MAPPER.treeToValue(targetNode, type);
    } catch (IllegalArgumentException | JsonProcessingException e) {
      throw new InvalidBodyException(null, null);
    }

  }

}
