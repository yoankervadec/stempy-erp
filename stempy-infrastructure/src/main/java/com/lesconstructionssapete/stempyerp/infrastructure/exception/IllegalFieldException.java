package com.lesconstructionssapete.stempyerp.infrastructure.exception;

import com.lesconstructionssapete.stempyerp.exception.ErrorCode;
import com.lesconstructionssapete.stempyerp.exception.InfrastructureException;

public class IllegalFieldException extends InfrastructureException {
  public IllegalFieldException(String message) {
    super(ErrorCode.ILLEGAL_FIELD.getCode(), message);
  }

}
