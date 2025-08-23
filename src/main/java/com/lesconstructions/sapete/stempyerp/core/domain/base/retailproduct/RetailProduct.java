package com.lesconstructions.sapete.stempyerp.core.domain.base.retailproduct;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.lesconstructions.sapete.stempyerp.core.domain.base.GenericEntity;
import com.lesconstructions.sapete.stempyerp.core.shared.constant.ConstantCache;
import com.lesconstructions.sapete.stempyerp.core.shared.constant.ConstantUtil;

public class RetailProduct extends GenericEntity {

  private static final String RETAIL_PRODUCT_ENTITY_NAME = "RETAIL PRODUCT";

  private String productNo;
  private BigDecimal retailPrice;
  private BigDecimal cost;
  private String description;
  private int retailCategoryId;
  private int woodSpecyId;
  private double productWidth;
  private double productThickness;
  private double productLength;
  private boolean isEnabled;

  public RetailProduct(
      long sequenceNo,
      String productNo,
      BigDecimal retailPrice,
      BigDecimal cost,
      String description,
      int retailCategoryId,
      int woodSpecyId,
      double productWidth,
      double productThickness,
      double productLength,
      boolean isEnabled,
      LocalDateTime createdAt,
      Long createdByUserSeq) {
    super(
        ConstantUtil.findByName(
            ConstantCache.getEntityTypes(),
            RETAIL_PRODUCT_ENTITY_NAME),
        sequenceNo,
        createdAt,
        createdByUserSeq);
    this.productNo = productNo;
    this.retailPrice = retailPrice;
    this.cost = cost;
    this.description = description;
    this.retailCategoryId = retailCategoryId;
    this.woodSpecyId = woodSpecyId;
    this.productWidth = productWidth;
    this.productThickness = productThickness;
    this.productLength = productLength;
    this.isEnabled = isEnabled;
  }

  public String getProductNo() {
    return productNo;
  }

  public void setProductNo(String productNo) {
    this.productNo = productNo;
  }

  public BigDecimal getRetailPrice() {
    return retailPrice;
  }

  public void setRetailPrice(BigDecimal retailPrice) {
    this.retailPrice = retailPrice;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getRetailCategoryId() {
    return retailCategoryId;
  }

  public void setRetailCategoryId(int retailCategoryId) {
    this.retailCategoryId = retailCategoryId;
  }

  public int getWoodSpecyId() {
    return woodSpecyId;
  }

  public void setWoodSpecyId(int woodSpecyId) {
    this.woodSpecyId = woodSpecyId;
  }

  public double getProductWidth() {
    return productWidth;
  }

  public void setProductWidth(double productWidth) {
    this.productWidth = productWidth;
  }

  public double getProductThickness() {
    return productThickness;
  }

  public void setProductThickness(double productThickness) {
    this.productThickness = productThickness;
  }

  public double getProductLength() {
    return productLength;
  }

  public void setProductLength(double productLength) {
    this.productLength = productLength;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public void setEnabled(boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

}
