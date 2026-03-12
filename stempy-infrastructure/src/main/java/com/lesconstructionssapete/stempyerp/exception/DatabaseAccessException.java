package com.lesconstructionssapete.stempyerp.exception;

public class DatabaseAccessException extends InfrastructureException {

  public DatabaseAccessException(String message, Throwable cause) {
    super(ErrorCode.DATABASE_ACCESS_ERROR.getCode(), message, cause);
  }
}
