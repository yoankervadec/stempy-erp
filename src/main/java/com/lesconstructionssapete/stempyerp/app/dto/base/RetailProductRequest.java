package com.lesconstructionssapete.stempyerp.app.dto.base;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RetailProductRequest {

  public long sequenceNo;
  public String productNo;
  public BigDecimal retailPrice;
  public BigDecimal cost;
  public String description;
  public int retailCategoryId;
  public int woodSpecyId;
  public double productWidth;
  public double productThickness;
  public double productLength;
  public boolean enabled;
  public LocalDateTime createdAt;
  public Long createdByUserSeq;

}
