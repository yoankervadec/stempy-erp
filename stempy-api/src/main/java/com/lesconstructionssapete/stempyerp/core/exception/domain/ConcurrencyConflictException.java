package com.lesconstructionssapete.stempyerp.core.exception.domain;

import com.lesconstructionssapete.stempyerp.core.exception.DomainException;
import com.lesconstructionssapete.stempyerp.core.exception.ErrorCode;

public class ConcurrencyConflictException extends DomainException {

  public static final String CODE = "CONCURRENCY_CONFLICT";

  public ConcurrencyConflictException(String message) {
    super(ErrorCode.CONFLICT, CODE, message);
  }
}