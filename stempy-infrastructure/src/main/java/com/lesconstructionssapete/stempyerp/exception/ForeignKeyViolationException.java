package com.lesconstructionssapete.stempyerp.exception;

public class ForeignKeyViolationException extends InfrastructureException {
  public ForeignKeyViolationException(String message, Throwable cause) {
    super(ErrorCode.DATABASE_ACCESS_ERROR.getCode(), message, cause);
  }

}
