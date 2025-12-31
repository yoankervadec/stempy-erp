package com.lesconstructionssapete.stempyerp.core.exception;

/**
 * Centralized error codes for the application.
 * 
 * Each error code has a unique string identifier and a default message.
 */

public enum ErrorCode {

  // API
  UNDEFINED_API_EXCEPTION("UNDEFINED_API_EXCEPTION", "Something went wrong"),
  BAD_REQUEST("BAD_REQUEST", "The request is invalid"),
  UNAUTHORIZED("UNAUTHORIZED", "You are not authorized"),
  FORBIDDEN("FORBIDDEN", "You do not have permission"),
  NOT_FOUND("NOT_FOUND", "Resource not found"),
  CONFLICT("CONFLICT", "Conflict with current state"),
  INVALID_BODY("INVALID_BODY", "The request body is invalid"),

  // DOMAIN
  UNDEFINED_DOMAIN_EXCEPTION("UNDEFINED_DOMAIN_EXCEPTION", "Undefined domain exception"),
  QUERY_LOAD_EXCEPTION("QUERY_LOAD_EXCEPTION", "Failed to load SQL file"),

  // INTERNAL
  UNDEFINED_SYSTEM_ERROR("SYSTEM_ERROR", "Unexpected internal system error"),
  DATABASE_ERROR("DATABASE_ERROR", "A database error occurred"),
  CONFIGURATION_ERROR("CONFIGURATION_ERROR", "Invalid configuration"),

  ;

  private final String code;
  private final String defaultMessage;

  ErrorCode(String code, String defaultMessage) {
    this.code = code;
    this.defaultMessage = defaultMessage;
  }

  public String getCode() {
    return code;
  }

  public String getDefaultMessage() {
    return defaultMessage;
  }

}
