package com.lesconstructions.sapete.stempyerp.core.exception;

/**
 * Abstract parent for all custom exceptions.
 * Holds common fields (message, optional cause, errorCode).
 */
abstract class BaseException extends RuntimeException {

  protected final String errorCode;

  protected BaseException(String message, String errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  protected BaseException(String message, String errorCode, Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }

}
