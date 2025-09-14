package com.lesconstructions.sapete.stempyerp.core.exception.domain;

import com.lesconstructions.sapete.stempyerp.core.exception.DomainException;

public class SequenceUpdateException extends DomainException {

  public SequenceUpdateException(String entityType, Throwable cause) {
    super("Failed to update sequence for entity type: " + entityType, null, cause);
  }
}
