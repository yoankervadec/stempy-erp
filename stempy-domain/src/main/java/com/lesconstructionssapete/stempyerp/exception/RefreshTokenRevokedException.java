package com.lesconstructionssapete.stempyerp.exception;

public class RefreshTokenRevokedException extends DomainException {

  public RefreshTokenRevokedException(String message) {
    super(ErrorCode.INVALID_TOKEN, null, message);
  }

}
