package com.lesconstructionssapete.stempyerp.exception;

public class ValidationException extends DomainException {

  public static final String CODE = "VALIDATION_ERROR";

  public ValidationException(String message) {
    super(ErrorCode.BAD_REQUEST, CODE, message);
  }
}