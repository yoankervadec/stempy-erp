package com.lesconstructionssapete.stempyerp.exception;

public class UnexpectedSystemException extends InternalException {

  public static final String CODE = "UNEXPECTED_ERROR";

  public UnexpectedSystemException(String message, Throwable cause) {
    super(ErrorCode.UNDEFINED_SYSTEM_ERROR, CODE, message, cause);
  }
}