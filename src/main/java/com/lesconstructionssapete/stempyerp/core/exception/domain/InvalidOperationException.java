package com.lesconstructionssapete.stempyerp.core.exception.domain;

import com.lesconstructionssapete.stempyerp.core.exception.DomainException;
import com.lesconstructionssapete.stempyerp.core.exception.ErrorCode;

public class InvalidOperationException extends DomainException {

  public static final String CODE = "INVALID_OPERATION";

  public InvalidOperationException(String message) {
    super(ErrorCode.BAD_REQUEST, CODE, message);
  }
}
