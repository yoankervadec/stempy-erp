package com.lesconstructionssapete.stempyerp.exception;

public class DuplicateKeyException extends InfrastructureException {
  public DuplicateKeyException(String message, Throwable cause) {
    super(ErrorCode.DATABASE_ACCESS_ERROR.getCode(), message, cause);
  }

}
