package com.lesconstructions.sapete.stempyerp.core.domain.generic;

import java.time.LocalDateTime;

import com.lesconstructions.sapete.stempyerp.core.domain.base.constant.EntityType;

/*
 * GenericEntity extended by entities like RetailProduct, CustomerOrder Header, etc...
 */

public abstract class GenericEntity {

  protected final EntityType entityType;
  protected String entityNo;
  protected final long entitySeq;
  protected LocalDateTime createdAt;
  protected Long createdByUserSeq;

  // New GenericEntity generates createdAt
  public GenericEntity(EntityType entityType, long entitySeq, Long createdByUserSeq) {
    this.entityType = entityType;
    this.entitySeq = entitySeq;
    this.createdAt = LocalDateTime.now();
    this.createdByUserSeq = createdByUserSeq;
  }

  // Existing GenericEntity gets entityNo & createdAt
  public GenericEntity(EntityType entityType, String entityNo, long entitySeq, LocalDateTime createdAt,
      Long createdByUserSeq) {
    this.entityType = entityType;
    this.entityNo = entityNo;
    this.entitySeq = entitySeq;
    this.createdAt = createdAt;
    this.createdByUserSeq = createdByUserSeq;
  }

  // Minimal existing GenericEntity without createdByUserSeq & createdAt
  public GenericEntity(EntityType entityType, String entityNo, long entitySeq) {
    this.entityType = entityType;
    this.entityNo = entityNo;
    this.entitySeq = entitySeq;
  }

  public EntityType getEntityType() {
    return entityType;
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

  public void setCreatedByUserSeq(Long createdByUserSeq) {
    this.createdByUserSeq = createdByUserSeq;
  }

}
