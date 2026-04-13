package com.lesconstructionssapete.stempyerp.domain.exception;

import com.lesconstructionssapete.stempyerp.exception.DomainException;
import com.lesconstructionssapete.stempyerp.exception.ErrorCode;

public class InvalidRefreshTokenException extends DomainException {

  public InvalidRefreshTokenException(String message) {
    super(ErrorCode.INVALID_TOKEN, null, message);
  }

}
