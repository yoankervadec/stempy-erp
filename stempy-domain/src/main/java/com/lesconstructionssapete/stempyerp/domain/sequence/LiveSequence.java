package com.lesconstructionssapete.stempyerp.domain.sequence;

import com.lesconstructionssapete.stempyerp.domain.constant.DomainEntityType;

/*
 * Holds the sequence number for a given EntityType before persisting.
 */

public class LiveSequence {

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
