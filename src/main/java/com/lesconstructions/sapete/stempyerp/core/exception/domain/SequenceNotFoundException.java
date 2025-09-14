package com.lesconstructions.sapete.stempyerp.core.exception.domain;

import com.lesconstructions.sapete.stempyerp.core.exception.BaseException;

public class SequenceNotFoundException extends BaseException {

  public SequenceNotFoundException(String entityType) {
    super("No sequence found to entity type: " + entityType, null, null);
  }
}
