package com.lesconstructionssapete.stempyerp.exception;

/**
 * For invariants and business logic.
 */
public abstract class DomainException extends BaseException implements ClientFacing {

  protected DomainException(ErrorCode fallback, String code, String message) {
    super(fallback, code, message);
  }

  protected DomainException(ErrorCode fallback, String code, String message, Throwable cause) {
    super(fallback, code, message, cause);
  }

  @Override
  public boolean exposeCause() {
    return true;
  }

}
