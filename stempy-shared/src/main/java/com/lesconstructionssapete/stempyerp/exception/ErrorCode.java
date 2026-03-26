package com.lesconstructionssapete.stempyerp.exception;

/**
 * Centralized error codes for the application.
 * 
 * Each error code has a unique string identifier and a default message.
 */

public enum ErrorCode {

  // API
  UNDEFINED_API_EXCEPTION("UNDEFINED_API_EXCEPTION", "Something went wrong"),
  BAD_REQUEST("BAD_REQUEST", "The request is invalid"),
  UNAUTHENTICATED("UNAUTHENTICATED", "You need to authenticate"),
  UNAUTHORIZED("UNAUTHORIZED", "You are not authorized"),
  FORBIDDEN("FORBIDDEN", "You do not have permission"),
  NOT_FOUND("NOT_FOUND", "Resource not found"),
  CONFLICT("CONFLICT", "Conflict with current state"),
  INVALID_BODY("INVALID_BODY", "The request body is invalid"),

  // DOMAIN
  UNDEFINED_DOMAIN_EXCEPTION("UNDEFINED_DOMAIN_EXCEPTION", "Undefined domain exception"),
  USER_NOT_FOUND("USER_NOT_FOUND", "User not found"),
  INVALID_TOKEN("INVALID_TOKEN", "The provided token is invalid"),
  QUERY_LOAD_EXCEPTION("QUERY_LOAD_EXCEPTION", "Failed to load SQL file"),
  FIELD_NOT_FOUND("FIELD_NOT_FOUND", "Field not found in query"),
  INVALID_CREDENTIALS("INVALID_CREDENTIALS", "Invalid user credentials"),

  // INFRASTRUCTURE
  UNDEFINED_INFRASTRUCTURE_EXCEPTION("UNDEFINED_INFRASTRUCTURE_EXCEPTION", "Undefined infrastructure exception"),
  CONNECTION_FAILURE("CONNECTION_FAILURE", "Failed to connect to the database"),
  DATABASE_ACCESS_ERROR("DATABASE_ACCESS_ERROR", "A database access error occurred"),
  SEQUENCE_UPDATE_FAILURE("SEQUENCE_UPDATE_FAILURE", "Failed to update sequence"),
  TRANSACTION_FAILURE("TRANSACTION_FAILURE", "Transaction failed"),

  // INTERNAL
  UNDEFINED_SYSTEM_ERROR("SYSTEM_ERROR", "Unexpected internal system error"),
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
