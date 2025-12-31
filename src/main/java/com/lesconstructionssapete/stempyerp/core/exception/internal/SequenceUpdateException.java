package com.lesconstructionssapete.stempyerp.core.exception.internal;

import com.lesconstructionssapete.stempyerp.core.exception.ErrorCode;
import com.lesconstructionssapete.stempyerp.core.exception.InternalException;

public class SequenceUpdateException extends InternalException {

  public SequenceUpdateException(String entityType, Throwable cause) {
    super(
        ErrorCode.UNDEFINED_SYSTEM_ERROR,
        ErrorCode.UNDEFINED_SYSTEM_ERROR.getCode(),
        "Failed to update sequence for entity type: " + entityType,
        cause);
  }
}
