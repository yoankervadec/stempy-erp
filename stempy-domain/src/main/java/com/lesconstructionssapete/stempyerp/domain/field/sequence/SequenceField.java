package com.lesconstructionssapete.stempyerp.domain.field.sequence;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;

public enum SequenceField implements DomainField {

  DOMAIN_ENTITY_ID("domainEntityId"),
  NEXT("next"),
  ENABLED("enabled"),
  CREATED_AT("createdAt");

  private static final String PREFIX = "Sequence.";
  private final String logicalName;

  SequenceField(String logicalName) {
    this.logicalName = logicalName;
  }

  @Override
  public String logicalName() {
    return PREFIX + logicalName;
  }

}
