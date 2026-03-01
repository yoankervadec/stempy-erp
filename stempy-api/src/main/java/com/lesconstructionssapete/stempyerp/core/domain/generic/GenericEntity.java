package com.lesconstructionssapete.stempyerp.core.domain.generic;

import java.time.LocalDateTime;

/*
 * GenericEntity extended by entities like RetailProduct, CustomerOrderHeader, etc...
 */

public abstract class GenericEntity {

  protected final String entityName;
  protected Long entityId;
  protected String entityNo;
  protected final LocalDateTime createdAt;
  protected final Long createdByUserId;

  // New GenericEntity generates createdAt
  public GenericEntity(
      String entityName,
      Long entityId,
      Long createdByUserId) {
    this.entityName = entityName;
    this.entityId = entityId;
    this.createdAt = LocalDateTime.now();
    this.createdByUserId = createdByUserId;
  }

  // Existing GenericEntity gets entityNo & createdAt
  public GenericEntity(
      String entityName,
      String entityNo,
      Long entityId,
      LocalDateTime createdAt,
      Long createdByUserId) {
    this.entityName = entityName;
    this.entityNo = entityNo;
    this.entityId = entityId;
    this.createdAt = createdAt;
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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public Long getCreatedByUserId() {
    return createdByUserId;
  }

}
