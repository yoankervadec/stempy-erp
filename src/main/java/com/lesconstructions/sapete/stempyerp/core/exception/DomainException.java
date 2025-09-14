package com.lesconstructions.sapete.stempyerp.core.exception;

/**
 * For invariants and business logic.
 */
public abstract class DomainException extends BaseException {

  protected DomainException(ErrorCode error) {
    super(error.getDefaultMessage(), error.getCode());
  }

  protected DomainException(String message, String errorCode) {
    super(
        message != null ? message : ErrorCode.UNDEFINED_DOMAIN_EXCEPTION.getDefaultMessage(),
        errorCode != null ? errorCode : ErrorCode.UNDEFINED_DOMAIN_EXCEPTION.getCode());
  }

  protected DomainException(ErrorCode error, Throwable cause) {
    super(error.getDefaultMessage(), error.getCode(), cause);
  }

  protected DomainException(String message, String errorCode, Throwable cause) {
    super(
        message != null ? message : ErrorCode.UNDEFINED_DOMAIN_EXCEPTION.getDefaultMessage(),
        errorCode != null ? errorCode : ErrorCode.UNDEFINED_DOMAIN_EXCEPTION.getCode(),
        cause);
  }
}
