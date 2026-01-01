package com.lesconstructionssapete.stempyerp.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lesconstructionssapete.stempyerp.app.http.ApiErrorFactory;
import com.lesconstructionssapete.stempyerp.app.http.Response;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class ExceptionConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionConfig.class);

  public static void configure(Javalin app) {

    // API EXCEPTIONS
    app.exception(com.lesconstructionssapete.stempyerp.core.exception.ApiException.class, (e, ctx) -> {
      LOGGER.error("API Exception : {}", e.getMessage());
      Response.error(
          ctx,
          e.getStatus(),
          e.getCode() + ": " + e.getMessage(),
          ApiErrorFactory.from(e));
    });

    // DOMAIN EXCEPTIONS
    app.exception(com.lesconstructionssapete.stempyerp.core.exception.DomainException.class, (e, ctx) -> {
      LOGGER.error("Domain Exception : {}", e.getMessage());
      Response.error(
          ctx,
          HttpStatus.BAD_REQUEST,
          e.getCode() + ": " + e.getMessage(),
          ApiErrorFactory.from(e));
    });

    // INTERNAL EXCEPTIONS
    app.exception(com.lesconstructionssapete.stempyerp.core.exception.InternalException.class, (e, ctx) -> {
      LOGGER.error("Internal Exception", e);
      Response.error(
          ctx,
          HttpStatus.INTERNAL_SERVER_ERROR,
          "INTERNAL_SERVER_ERROR: An unexpected error occurred.",
          null);
    });

    // UNCAUGHT EXCEPTIONS
    app.exception(Exception.class, (e, ctx) -> {
      LOGGER.error("Uncaught Exception", e);
      Response.error(
          ctx,
          HttpStatus.INTERNAL_SERVER_ERROR,
          "UNCAUGHT_EXCEPTION: Something went wrong.",
          null);
    });
  }

}
