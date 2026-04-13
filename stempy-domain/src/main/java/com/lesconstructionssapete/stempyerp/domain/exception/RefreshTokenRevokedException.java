package com.lesconstructionssapete.stempyerp.domain.exception;

import com.lesconstructionssapete.stempyerp.exception.DomainException;
import com.lesconstructionssapete.stempyerp.exception.ErrorCode;

public class RefreshTokenRevokedException extends DomainException {

  public RefreshTokenRevokedException(String message) {
    super(ErrorCode.INVALID_TOKEN, null, message);
  }

}
