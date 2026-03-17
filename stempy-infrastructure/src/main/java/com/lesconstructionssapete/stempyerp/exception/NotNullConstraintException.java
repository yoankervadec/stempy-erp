package com.lesconstructionssapete.stempyerp.exception;

public class NotNullConstraintException extends InfrastructureException {
  public NotNullConstraintException(String message, Throwable cause) {
    super(ErrorCode.DATABASE_ACCESS_ERROR.getCode(), message, cause);
  }

}
