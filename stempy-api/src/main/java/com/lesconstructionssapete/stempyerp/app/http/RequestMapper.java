package com.lesconstructionssapete.stempyerp.app.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesconstructionssapete.stempyerp.core.exception.api.InvalidBodyException;
import com.lesconstructionssapete.stempyerp.core.shared.util.JsonUtil;

/**
 * Request Mapper:
 * - Utility to map JSON body parts to Java objects
 * - Uses Jackson for flexible mapping and error handling
 * - Designed for use in controllers to convert API requests to service layer
 * DTOs
 */
public final class RequestMapper {

  private RequestMapper() {
    // Static class
  }

  private static final ObjectMapper MAPPER = JsonUtil.mapper();

  /**
   * Maps a specific part of the JSON body to a Java object of the given type.
   *
   * @param json     The root JSON node containing the body
   * @param type     The target Java class to map to
   * @param bodyPart The specific part of the body to map (e.g. PAYLOAD, META)
   * @return An instance of the target type mapped from the specified body part
   * @throws InvalidBodyException if mapping fails due to invalid structure or
   *                              content
   */
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
