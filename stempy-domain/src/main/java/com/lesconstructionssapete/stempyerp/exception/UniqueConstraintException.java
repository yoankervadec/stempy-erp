package com.lesconstructionssapete.stempyerp.exception;

public class UniqueConstraintException extends DomainException {

  public UniqueConstraintException(String message) {
    super(ErrorCode.UNIQUE_CONSTRAINT_VIOLATION, null, message);
  }

}
