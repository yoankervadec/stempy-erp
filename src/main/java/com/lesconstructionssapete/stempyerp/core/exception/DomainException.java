package com.lesconstructionssapete.stempyerp.core.exception;

/**
 * For invariants and business logic.
 */
public abstract class DomainException extends BaseException {

  protected DomainException(ErrorCode fallback, String code, String message) {
    super(fallback, code, message);
  }

  protected DomainException(ErrorCode fallback, String code, String message, Throwable cause) {
    super(fallback, code, message, cause);
  }

}
