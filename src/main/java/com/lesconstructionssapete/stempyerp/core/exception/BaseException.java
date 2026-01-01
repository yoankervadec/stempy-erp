package com.lesconstructionssapete.stempyerp.core.exception;

/**
 * Abstract parent for all custom exceptions.
 * Holds common fields (message, optional cause, errorCode).
 */
abstract class BaseException extends RuntimeException {

  protected final String code;
  protected final String description;

  // Constructors with various combinations of parameters
  protected BaseException(ErrorCode fallback, String code, String message) {
    super(resolveMessage(message, fallback));
    this.code = resolveCode(code, fallback);
    this.description = null;
  }

  protected BaseException(ErrorCode fallback, String code, String message, String description) {
    super(resolveMessage(message, fallback));
    this.code = resolveCode(code, fallback);
    this.description = description;
  }

  protected BaseException(ErrorCode fallback, String code, String message, Throwable cause) {
    super(resolveMessage(message, fallback), cause);
    this.code = resolveCode(code, fallback);
    this.description = null;
  }

  protected BaseException(ErrorCode fallback, String code, String message, String description, Throwable cause) {
    super(resolveMessage(message, fallback), cause);
    this.code = resolveCode(code, fallback);
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

  // Helper methods to safely resolve message and code
  protected static String resolveMessage(String message, ErrorCode fallback) {
    return message != null ? message : fallback.getDefaultMessage();
  }

  protected static String resolveCode(String code, ErrorCode fallback) {
    return code != null ? code : fallback.getCode();
  }

}
