package com.lesconstructionssapete.stempyerp.core.exception.domain;

import com.lesconstructionssapete.stempyerp.core.exception.DomainException;
import com.lesconstructionssapete.stempyerp.core.exception.ErrorCode;

public class ValidationException extends DomainException {

  public static final String CODE = "VALIDATION_ERROR";

  public ValidationException(String message) {
    super(ErrorCode.BAD_REQUEST, CODE, message);
  }
}