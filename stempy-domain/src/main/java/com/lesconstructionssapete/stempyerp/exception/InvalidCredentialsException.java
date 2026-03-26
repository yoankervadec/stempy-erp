package com.lesconstructionssapete.stempyerp.exception;

public class InvalidCredentialsException extends DomainException {

  public InvalidCredentialsException(String message) {
    super(ErrorCode.INVALID_CREDENTIALS, null, message);
  }

}
