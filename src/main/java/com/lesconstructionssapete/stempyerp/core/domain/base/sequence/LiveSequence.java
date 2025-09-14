package com.lesconstructionssapete.stempyerp.core.domain.base.sequence;

import com.lesconstructionssapete.stempyerp.core.domain.base.constant.EntityType;

/*
 * Holds a sequence number for a given EntityType.
 * Provides generateEntityNo to generate the entity number
 * baed on the EntityType (e.g., RP000010).
 */

public class LiveSequence {

  private final EntityType entityType;
  private final long sequenceNo;
  private final Long createdByUserSeq;

  public LiveSequence(EntityType entityType, long sequenceNo, Long createdByUserSeq) {
    this.entityType = entityType;
    this.sequenceNo = sequenceNo;
    this.createdByUserSeq = createdByUserSeq;
  }

  public EntityType getEntityType() {
    return entityType;
  }

  public long getSequenceNo() {
    return sequenceNo;
  }

  public String getEntityNo() {
    return generateEntityNo();
  }

  public Long getCreatedByUserSeq() {
    return createdByUserSeq;
  }

  private String generateEntityNo() {
    String formattedEntityNo = String.format("%0" + entityType.getPadLength() + "d", sequenceNo);
    return entityType.getPrefixString() + formattedEntityNo;
  }

}
