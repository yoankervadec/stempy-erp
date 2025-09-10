package com.lesconstructions.sapete.stempyerp.app.dto.retailproduct;

import java.math.BigDecimal;

public class RetailProductDto {

  private String productNo;
  private BigDecimal retailPrice;
  private BigDecimal cost;
  private String description;
  private Integer retailCategoryId;
  private Integer woodSpecyId;
  private Double productWidth;
  private Double productThickness;
  private Double productLength;
  private Boolean isEnabled;

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

  public Integer getRetailCategoryId() {
    return retailCategoryId;
  }

  public void setRetailCategoryId(Integer retailCategoryId) {
    this.retailCategoryId = retailCategoryId;
  }

  public Integer getWoodSpecyId() {
    return woodSpecyId;
  }

  public void setWoodSpecyId(Integer woodSpecyId) {
    this.woodSpecyId = woodSpecyId;
  }

  public Double getProductWidth() {
    return productWidth;
  }

  public void setProductWidth(Double productWidth) {
    this.productWidth = productWidth;
  }

  public Double getProductThickness() {
    return productThickness;
  }

  public void setProductThickness(Double productThickness) {
    this.productThickness = productThickness;
  }

  public Double getProductLength() {
    return productLength;
  }

  public void setProductLength(Double productLength) {
    this.productLength = productLength;
  }

  public Boolean getIsEnabled() {
    return isEnabled;
  }

  public void setIsEnabled(Boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

}
