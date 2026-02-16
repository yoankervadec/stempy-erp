package com.lesconstructionssapete.stempyerp.core.domain.generic;

import java.time.LocalDateTime;

/*
 * GenericEntity extended by entities like RetailProduct, CustomerOrder Header, etc...
 */

public abstract class GenericEntity {

  protected final String entityTypeName;
  protected String entityNo;
  protected final long entitySeq;
  protected LocalDateTime createdAt;
  protected final long createdByUserSeq;

  // New GenericEntity generates createdAt
  public GenericEntity(String entityTypeName, long entitySeq, long createdByUserSeq) {
    this.entityTypeName = entityTypeName;
    this.entitySeq = entitySeq;
    this.createdAt = LocalDateTime.now();
    this.createdByUserSeq = createdByUserSeq;
  }

  // Existing GenericEntity gets entityNo & createdAt
  public GenericEntity(String entityTypeName, String entityNo, long entitySeq, LocalDateTime createdAt,
      long createdByUserSeq) {
    this.entityTypeName = entityTypeName;
    this.entityNo = entityNo;
    this.entitySeq = entitySeq;
    this.createdAt = createdAt;
    this.createdByUserSeq = createdByUserSeq;
  }

  // Minimal existing GenericEntity without createdByUserSeq & createdAt
  public GenericEntity(String entityTypeName, String entityNo, long entitySeq, long createdByUserSeq) {
    this.entityTypeName = entityTypeName;
    this.entityNo = entityNo;
    this.entitySeq = entitySeq;
    this.createdByUserSeq = createdByUserSeq;
  }

  public String getEntityTypeName() {
    return entityTypeName;
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

  public long getCreatedByUserSeq() {
    return createdByUserSeq;
  }

}
