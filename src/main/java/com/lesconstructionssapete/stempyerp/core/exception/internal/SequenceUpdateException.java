package com.lesconstructionssapete.stempyerp.core.exception.internal;

import com.lesconstructionssapete.stempyerp.core.exception.InternalException;

public class SequenceUpdateException extends InternalException {

  public SequenceUpdateException(String entityType, Throwable cause) {
    super("Failed to update sequence for entity type: " + entityType, null, cause);
  }
}
