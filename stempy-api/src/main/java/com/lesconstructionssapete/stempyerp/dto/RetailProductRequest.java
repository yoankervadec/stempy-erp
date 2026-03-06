package com.lesconstructionssapete.stempyerp.dto;

import java.time.LocalDateTime;

public class RetailProductRequest {

  public Long retailProductId;
  public Long retailProductMasterId;

  public String retailProductNo;
  public String retailProductVariantNo;

  public String name;
  public String description;

  public boolean enabled;

  public LocalDateTime createdAt;
  public Long createdByUserId;
  public LocalDateTime updatedAt;
  public Long updatedByUserId;

}
