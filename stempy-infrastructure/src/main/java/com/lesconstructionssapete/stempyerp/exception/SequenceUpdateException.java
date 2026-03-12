package com.lesconstructionssapete.stempyerp.exception;

public class SequenceUpdateException extends InfrastructureException {

  public SequenceUpdateException(String entityType, Throwable cause) {
    super(ErrorCode.SEQUENCE_UPDATE_FAILURE.getCode(), "Failed to update sequence for entity: " + entityType, cause);
  }
}
