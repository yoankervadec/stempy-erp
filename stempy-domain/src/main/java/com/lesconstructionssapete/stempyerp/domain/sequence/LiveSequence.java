package com.lesconstructionssapete.stempyerp.domain.sequence;

import com.lesconstructionssapete.stempyerp.domain.constant.DomainEntityType;
import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.domain.field.FieldMeta;

/*
 * Holds the sequence number for a given EntityType before persisting.
 */

public class LiveSequence {

  public enum Fields implements EntityField {

    DOMAIN_ENTITY_ID(FieldMeta.builder("LiveSequence", "core_domain_entity_sequence")
        .field("domainEntityId", "domain_entity_id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    NEXT(FieldMeta.builder("LiveSequence", "core_domain_entity_sequence")
        .field("next", "next")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    ENABLED(FieldMeta.builder("LiveSequence", "core_domain_entity_sequence")
        .field("enabled", "enabled")
        .type(Boolean.class, java.sql.Types.BOOLEAN)
        .notNullable()
        .build()),

    CREATED_AT(FieldMeta.builder("LiveSequence", "core_domain_entity_sequence")
        .field("createdAt", "created_at")
        .type(java.sql.Timestamp.class, java.sql.Types.TIMESTAMP)
        .notNullable()
        .build());

    private final FieldMeta meta;

    Fields(FieldMeta meta) {
      this.meta = meta;
    }

    @Override
    public FieldMeta meta() {
      return meta;
    }

  }

  private final DomainEntityType entityType;
  private final long sequenceNo;
  private final long createdByUserSeq;

  public LiveSequence(DomainEntityType entityType, long sequenceNo, long createdByUserSeq) {
    this.entityType = entityType;
    this.sequenceNo = sequenceNo;
    this.createdByUserSeq = createdByUserSeq;
  }

  public DomainEntityType getEntityType() {
    return entityType;
  }

  public long getSequenceNo() {
    return sequenceNo;
  }

  public long getCreatedByUserSeq() {
    return createdByUserSeq;
  }

}
