package com.lesconstructionssapete.stempyerp.core.exception.internal;

import com.lesconstructionssapete.stempyerp.core.exception.ErrorCode;
import com.lesconstructionssapete.stempyerp.core.exception.InternalException;

public class DatabaseAccessException extends InternalException {
  public static final String CODE = "DB_ACCESS_ERROR";

  public DatabaseAccessException(String message, Throwable cause) {
    super(ErrorCode.DATABASE_ERROR, CODE, message, cause);
  }
}
