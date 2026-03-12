package com.lesconstructionssapete.stempyerp.exception;

public class UserNotFoundException extends DomainException {

  public UserNotFoundException(String message) {
    super(ErrorCode.USER_NOT_FOUND, null, message);
  }
}
