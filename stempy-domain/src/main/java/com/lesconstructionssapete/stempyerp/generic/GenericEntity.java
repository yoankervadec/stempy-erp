package com.lesconstructionssapete.stempyerp.generic;

import java.time.Instant;

/*
 * GenericEntity extended by entities like RetailProduct, CustomerOrderHeader, etc...
 */

public abstract class GenericEntity {

  protected final String entityName;
  protected Long entityId;
  protected String entityNo;
  protected final Instant createdAt;
  protected final Long createdByUserId;

  // New GenericEntity generates createdAt
  public GenericEntity(
      String entityName,
      Long entityId,
      Long createdByUserId) {
    this.entityName = entityName;
    this.entityId = entityId;
    this.createdAt = Instant.now();
    this.createdByUserId = createdByUserId;
  }

  // Existing GenericEntity gets entityNo & createdAt
  public GenericEntity(
      String entityName,
      String entityNo,
      Long entityId,
      Instant createdAt,
      Long createdByUserId) {
    this.entityName = entityName;
    this.entityNo = entityNo;
    this.entityId = entityId;
    this.createdAt = createdAt == null ? Instant.now() : createdAt;
    this.createdByUserId = createdByUserId;
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

  public Long getEntityId() {
    return entityId;
  }

  public void setEntityId(Long entityId) {
    this.entityId = entityId;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Long getCreatedByUserId() {
    return createdByUserId;
  }

}
