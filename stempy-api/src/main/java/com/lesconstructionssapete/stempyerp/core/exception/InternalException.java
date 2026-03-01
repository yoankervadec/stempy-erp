package com.lesconstructionssapete.stempyerp.core.exception;

/**
 * Used for unnexpected or technical failures.
 * Never serialize to the client directly, only logged.
 * For infrastructure, persistence, I/O, unexpected logic errors.
 * Wrap low-level exceptions (e.g., SQL, IO, etc).
 */
public abstract class InternalException extends BaseException {

  protected InternalException(ErrorCode fallback, String code, String message) {
    super(fallback, code, message);
  }

  protected InternalException(ErrorCode fallback, String code, String message, Throwable cause) {
    super(fallback, code, message, cause);
  }

}
