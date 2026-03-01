package com.lesconstructionssapete.stempyerp.exception;

public class SequenceNotFoundException extends DomainException {

  public SequenceNotFoundException(String entityType) {
    super(
        ErrorCode.UNDEFINED_SYSTEM_ERROR,
        ErrorCode.UNDEFINED_SYSTEM_ERROR.getCode(),
        "No sequence found to entity type: " + entityType);
  }
}
