package com.lesconstructions.sapete.stempyerp.core.exception.internal;

import com.lesconstructions.sapete.stempyerp.core.exception.InternalException;

public class SequenceUpdateException extends InternalException {

  public SequenceUpdateException(String entityType, Throwable cause) {
    super("Failed to update sequence for entity type: " + entityType, null, cause);
  }
}
