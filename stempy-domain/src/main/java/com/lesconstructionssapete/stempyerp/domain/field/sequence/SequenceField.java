package com.lesconstructionssapete.stempyerp.domain.field.sequence;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldProvider;

public enum SequenceField implements DomainFieldProvider {

  DOMAIN_ENTITY_ID(new DomainField("Sequence", "domainEntityId", Long.class, false, true, true, "domain_entity_id",
      "core_domain_entity_sequence",
      java.sql.Types.BIGINT)),
  NEXT(new DomainField("Sequence", "next", Long.class, false, false, false, "next", "core_domain_entity_sequence",
      java.sql.Types.BIGINT)),
  ENABLED(new DomainField("Sequence", "enabled", Boolean.class, false, false, false, "enabled",
      "core_domain_entity_sequence",
      java.sql.Types.BOOLEAN)),
  CREATED_AT(new DomainField("Sequence", "createdAt", java.sql.Timestamp.class, false, false, false, "created_at",
      "core_domain_entity_sequence",
      java.sql.Types.TIMESTAMP));

  private final DomainField attribute;

  SequenceField(DomainField attribute) {
    this.attribute = attribute;
  }

  @Override
  public DomainField attribute() {
    return attribute;
  }

}
