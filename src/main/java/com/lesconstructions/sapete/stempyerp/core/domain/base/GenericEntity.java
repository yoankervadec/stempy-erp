package com.lesconstructions.sapete.stempyerp.core.domain.base;

import java.time.LocalDateTime;

import com.lesconstructions.sapete.stempyerp.core.domain.base.constant.EntityType;

/*
 * GenericEntity extended by entities like RetailProduct, CustomerOrder Header, etc...
 */

public abstract class GenericEntity {

  protected final EntityType entityType;
  protected String entityNo;
  protected final long sequenceNo;
  protected final LocalDateTime createdAt;
  protected final Long createdByUserSeq;

  // New GenericEntity generates createdAt
  public GenericEntity(EntityType entityType, long sequenceNo, Long createdByUserSeq) {
    this.entityType = entityType;
    this.sequenceNo = sequenceNo;
    this.createdAt = LocalDateTime.now();
    this.createdByUserSeq = createdByUserSeq;
  }

  // Existing GenericEntity gets createdAt
  public GenericEntity(EntityType entityType, long sequenceNo, LocalDateTime createdAt, Long createdByUserSeq) {
    this.entityType = entityType;
    this.sequenceNo = sequenceNo;
    this.createdAt = createdAt;
    this.createdByUserSeq = createdByUserSeq;
  }

  public String getEntityNo() {
    return entityNo;
  }

  public void setEntityNo(String entityNo) {
    this.entityNo = entityNo;
  }

  public long getSequenceNo() {
    return sequenceNo;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public Long getCreatedByUserSeq() {
    return createdByUserSeq;
  }

}
