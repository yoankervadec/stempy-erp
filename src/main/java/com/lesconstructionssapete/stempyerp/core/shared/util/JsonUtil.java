package com.lesconstructionssapete.stempyerp.core.shared.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtil {

  private static final ObjectMapper mapper = new ObjectMapper()
      .registerModule(new JavaTimeModule())
      .findAndRegisterModules()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  public static ObjectMapper getMapper() {
    return mapper;
  }

}
