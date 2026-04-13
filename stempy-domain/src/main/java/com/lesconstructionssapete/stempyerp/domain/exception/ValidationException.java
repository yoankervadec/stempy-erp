package com.lesconstructionssapete.stempyerp.domain.exception;

import com.lesconstructionssapete.stempyerp.exception.DomainException;
import com.lesconstructionssapete.stempyerp.exception.ErrorCode;

public class ValidationException extends DomainException {

  public static final String CODE = "VALIDATION_ERROR";

  public ValidationException(String message) {
    super(ErrorCode.BAD_REQUEST, CODE, message);
  }
}