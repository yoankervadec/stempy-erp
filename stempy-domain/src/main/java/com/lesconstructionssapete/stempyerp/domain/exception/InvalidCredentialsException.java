package com.lesconstructionssapete.stempyerp.domain.exception;

import com.lesconstructionssapete.stempyerp.exception.DomainException;
import com.lesconstructionssapete.stempyerp.exception.ErrorCode;

public class InvalidCredentialsException extends DomainException {

  public InvalidCredentialsException(String message) {
    super(ErrorCode.INVALID_CREDENTIALS, null, message);
  }

}
