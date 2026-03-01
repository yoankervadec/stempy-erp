package com.lesconstructionssapete.stempyerp.exception;

public class DatabaseAccessException extends InternalException {
  public static final String CODE = "DB_ACCESS_ERROR";

  public DatabaseAccessException(String message, Throwable cause) {
    super(ErrorCode.DATABASE_ERROR, CODE, message, cause);
  }
}
