package com.lesconstructionssapete.stempyerp.infrastructure.exception;

import com.lesconstructionssapete.stempyerp.exception.ErrorCode;
import com.lesconstructionssapete.stempyerp.exception.InfrastructureException;

public class NotNullConstraintException extends InfrastructureException {
  public NotNullConstraintException(String message, Throwable cause) {
    super(ErrorCode.DATABASE_ACCESS_ERROR.getCode(), message, cause);
  }

}
