package com.lesconstructionssapete.stempyerp.core.domain.generic;

import java.time.LocalDateTime;

/*
 * GenericEntity extended by entities like RetailProduct, CustomerOrder Header, etc...
 */

public abstract class GenericEntity {

  protected final String entityName;
  protected String entityNo;
  protected final long entitySeq;
  protected LocalDateTime createdAt;
  protected final Long createdByUserSeq;

  // New GenericEntity generates createdAt
  public GenericEntity(String entityName, long entitySeq, Long createdByUserSeq) {
    this.entityName = entityName;
    this.entitySeq = entitySeq;
    this.createdAt = LocalDateTime.now();
    this.createdByUserSeq = createdByUserSeq;
  }

  // Existing GenericEntity gets entityNo & createdAt
  public GenericEntity(String entityName, String entityNo, long entitySeq, LocalDateTime createdAt,
      Long createdByUserSeq) {
    this.entityName = entityName;
    this.entityNo = entityNo;
    this.entitySeq = entitySeq;
    this.createdAt = createdAt;
    this.createdByUserSeq = createdByUserSeq;
  }

  public String getEntityName() {
    return entityName;
  }

  public String getEntityNo() {
    return entityNo;
  }

  public void setEntityNo(String entityNo) {
    this.entityNo = entityNo;
  }

  public long getEntitySeq() {
    return entitySeq;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public Long getCreatedByUserSeq() {
    return createdByUserSeq;
  }

}
