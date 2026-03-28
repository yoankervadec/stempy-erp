package com.lesconstructionssapete.stempyerp.field.sequence;

import com.lesconstructionssapete.stempyerp.field.DomainField;

public enum SequenceField implements DomainField {

  DOMAIN_ENTITY_ID("domainEntityId"),
  NEXT("next"),
  ENABLED("enabled"),
  CREATED_AT("createdAt");

  private final String logicalName;

  SequenceField(String logicalName) {
    this.logicalName = logicalName;
  }

  @Override
  public String logicalName() {
    return logicalName;
  }

}
