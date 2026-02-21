package com.lesconstructionssapete.stempyerp.core.domain.base.sequence;

import com.lesconstructionssapete.stempyerp.core.domain.base.constant.EntityType;

/*
 * Holds the sequence number for a given EntityType before persisting.
 */

public class LiveSequence {

  private final EntityType entityType;
  private final long sequenceNo;
  private final long createdByUserSeq;

  public LiveSequence(EntityType entityType, long sequenceNo, long createdByUserSeq) {
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

  public long getCreatedByUserSeq() {
    return createdByUserSeq;
  }

}
