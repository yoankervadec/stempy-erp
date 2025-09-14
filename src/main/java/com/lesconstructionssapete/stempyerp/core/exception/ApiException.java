package com.lesconstructionssapete.stempyerp.core.exception;

/**
 * Use for client-facing errors (e.g., bad request, not found, etc).
 * No stack trace exposed to the client, serialize errorCode & message.
 */
public abstract class ApiException extends BaseException {

  protected final int httpStatus;

  protected ApiException(ErrorCode error, int httpStatus) {
    super(error.getDefaultMessage(), error.getCode());
    this.httpStatus = httpStatus;
  }

  protected ApiException(String message, String errorCode, int httpStatus) {
    super(
        message != null ? message : ErrorCode.UNDEFINED_API_EXCEPTION.getDefaultMessage(),
        errorCode != null ? errorCode : ErrorCode.UNDEFINED_API_EXCEPTION.getCode());
    this.httpStatus = httpStatus;
  }

  protected ApiException(ErrorCode error, int httpStatus, Throwable cause) {
    super(error.getDefaultMessage(), error.getCode(), cause);
    this.httpStatus = httpStatus;
  }

  protected ApiException(String message, String errorCode, int httpStatus, Throwable cause) {
    super(
        message != null ? message : ErrorCode.UNDEFINED_API_EXCEPTION.getDefaultMessage(),
        errorCode != null ? errorCode : ErrorCode.UNDEFINED_API_EXCEPTION.getCode(),
        cause);
    this.httpStatus = httpStatus;
  }

  public int getHttpStatus() {
    return httpStatus;
  }

}
