package com.lesconstructionssapete.stempyerp.app.config;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.javalin.Javalin;

public class ExceptionConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionConfig.class);

  public static void configure(Javalin app) {
    app.exception(com.lesconstructionssapete.stempyerp.core.exception.ApiException.class, (e, ctx) -> {
      LOGGER.error("API Exception : {}", e.getMessage());
      ctx.status(e.getHttpStatus());
      ctx.json(Map.of("errorCode", e.getErrorCode(), "message", e.getMessage()));
    });

    app.exception(com.lesconstructionssapete.stempyerp.core.exception.InternalException.class, (e, ctx) -> {
      LOGGER.error("Internal Exception", e);
      ctx.status(500).json(Map.of("errorCode", "INTERNAL_SERVER_ERROR", "message", "An unexpected error occurred."));
    });

    app.exception(Exception.class, (e, ctx) -> {
      LOGGER.error("Uncaught Exception", e);
      ctx.status(500).json(Map.of("errorCode", "UNCAUGHT_EXCEPTION", "message", "Something went wrong."));
    });
  }

}
