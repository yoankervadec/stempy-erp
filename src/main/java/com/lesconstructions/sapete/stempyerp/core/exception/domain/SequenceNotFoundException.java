package com.lesconstructions.sapete.stempyerp.core.exception.domain;

import com.lesconstructions.sapete.stempyerp.core.exception.DomainException;

public class SequenceNotFoundException extends DomainException {

  public SequenceNotFoundException(String entityType) {
    super("No sequence found to entity type: " + entityType, null, null);
  }
}
