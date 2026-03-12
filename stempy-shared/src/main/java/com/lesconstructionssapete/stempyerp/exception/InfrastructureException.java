package com.lesconstructionssapete.stempyerp.exception;

public abstract class InfrastructureException extends BaseException implements ClientFacing {

  public InfrastructureException(String code, String message) {
    super(ErrorCode.UNDEFINED_INFRASTRUCTURE_EXCEPTION, code, message);
  }

  public InfrastructureException(String code, String message, Throwable cause) {
    super(ErrorCode.UNDEFINED_INFRASTRUCTURE_EXCEPTION, code, message, cause);
  }

  public InfrastructureException(ErrorCode errorCode) {
    super(errorCode, null, null);
  }

  public InfrastructureException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, null, null, cause);
  }

  @Override
  public boolean exposeCause() {
    return false;
  }

}
