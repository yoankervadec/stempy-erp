package com.lesconstructionssapete.stempyerp.dto;

import java.time.Instant;

public class RetailProductRequest {

  public Long retailProductId;
  public Long retailProductMasterId;

  public String retailProductNo;
  public String retailProductVariantNo;

  public String name;
  public String description;

  public boolean enabled;

  public Instant createdAt;
  public Long createdByUserId;
  public Instant updatedAt;
  public Long updatedByUserId;

}
