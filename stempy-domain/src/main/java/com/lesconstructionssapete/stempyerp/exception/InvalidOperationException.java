package com.lesconstructionssapete.stempyerp.exception;

public class InvalidOperationException extends DomainException {

  public static final String CODE = "INVALID_OPERATION";

  public InvalidOperationException(String message) {
    super(ErrorCode.BAD_REQUEST, CODE, message);
  }
}
