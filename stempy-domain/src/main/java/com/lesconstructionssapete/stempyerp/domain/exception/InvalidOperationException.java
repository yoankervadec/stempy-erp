package com.lesconstructionssapete.stempyerp.domain.exception;

import com.lesconstructionssapete.stempyerp.exception.DomainException;
import com.lesconstructionssapete.stempyerp.exception.ErrorCode;

public class InvalidOperationException extends DomainException {

  public static final String CODE = "INVALID_OPERATION";

  public InvalidOperationException(String message) {
    super(ErrorCode.BAD_REQUEST, CODE, message);
  }
}
