package com.lesconstructionssapete.stempyerp.core.exception;

import io.javalin.http.HttpStatus;

/**
 * Use for client-facing errors (e.g., bad request, not found, etc).
 * No stack trace exposed to the client, serialize errorCode & message.
 */
public abstract class ApiException extends BaseException implements ClientFacing {

  protected final HttpStatus status;

  protected ApiException(ErrorCode fallback, String code, String message, HttpStatus status) {
    super(fallback, code, message);
    this.status = status;
  }

  protected ApiException(ErrorCode fallback, String code, String message, HttpStatus status, Throwable cause) {
    super(fallback, code, message, cause);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }

  @Override
  public boolean exposeCause() {
    return true;
  }

}
