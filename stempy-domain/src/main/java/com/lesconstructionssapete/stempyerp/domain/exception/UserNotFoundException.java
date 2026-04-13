package com.lesconstructionssapete.stempyerp.domain.exception;

import com.lesconstructionssapete.stempyerp.exception.DomainException;
import com.lesconstructionssapete.stempyerp.exception.ErrorCode;

public class UserNotFoundException extends DomainException {

  public UserNotFoundException(String message) {
    super(ErrorCode.USER_NOT_FOUND, null, message);
  }
}
