package com.lesconstructionssapete.stempyerp.domain.exception;

import com.lesconstructionssapete.stempyerp.exception.DomainException;
import com.lesconstructionssapete.stempyerp.exception.ErrorCode;

public class UniqueConstraintException extends DomainException {

  public UniqueConstraintException(String message) {
    super(ErrorCode.UNIQUE_CONSTRAINT_VIOLATION, null, message);
  }

}
