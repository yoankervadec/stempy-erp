package com.lesconstructionssapete.stempyerp.domain.exception;

import com.lesconstructionssapete.stempyerp.exception.DomainException;
import com.lesconstructionssapete.stempyerp.exception.ErrorCode;

public class ConcurrencyConflictException extends DomainException {

  public static final String CODE = "CONCURRENCY_CONFLICT";

  public ConcurrencyConflictException(String message) {
    super(ErrorCode.CONFLICT, CODE, message);
  }
}