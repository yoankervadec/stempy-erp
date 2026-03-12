package com.lesconstructionssapete.stempyerp.exception;

public class InvalidRefreshTokenException extends DomainException {

  public InvalidRefreshTokenException(String message) {
    super(ErrorCode.INVALID_TOKEN, null, message);
  }

}
