package com.lesconstructionssapete.stempyerp.exception;


public class ConcurrencyConflictException extends DomainException {

  public static final String CODE = "CONCURRENCY_CONFLICT";

  public ConcurrencyConflictException(String message) {
    super(ErrorCode.CONFLICT, CODE, message);
  }
}