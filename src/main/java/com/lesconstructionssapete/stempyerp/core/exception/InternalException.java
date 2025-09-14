package com.lesconstructionssapete.stempyerp.core.exception;

/**
 * Used for unnexpected or technical failures.
 * Never serialize to the client directly, only logged.
 * For infrastructure, persistence, I/O, unexpected logic errors.
 * Wrap low-level exceptions (e.g., SQL, IO, etc).
 */
public class InternalException extends BaseException {

  protected InternalException(Throwable cause) {
    this(ErrorCode.SYSTEM_ERROR, cause);
  }

  protected InternalException(ErrorCode error) {
    super(error.getDefaultMessage(), error.getCode());
  }

  protected InternalException(String message, String errorCode) {
    super(
        message != null ? message : ErrorCode.SYSTEM_ERROR.getDefaultMessage(),
        errorCode != null ? errorCode : ErrorCode.SYSTEM_ERROR.getCode());
  }

  protected InternalException(ErrorCode error, Throwable cause) {
    super(error.getDefaultMessage(), error.getCode(), cause);
  }

  protected InternalException(String message, String errorCode, Throwable cause) {
    super(
        message != null ? message : ErrorCode.SYSTEM_ERROR.getDefaultMessage(),
        errorCode != null ? errorCode : ErrorCode.SYSTEM_ERROR.getCode(),
        cause);
  }

}
