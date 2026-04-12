package com.lesconstructionssapete.stempyerp.retailproduct;

public class RetailProductMasterPolicy {

  private Long retailProductMasterId;
  private boolean discontiued;
  private boolean trackInventory;
  private boolean allowNegativeInventory;
  private boolean applyTax;
  private boolean applyPromotion;

  public RetailProductMasterPolicy(
      Long retailProductMasterId,
      boolean discontiued,
      boolean trackInventory,
      boolean allowNegativeInventory,
      boolean applyTax,
      boolean applyPromotion) {
    this.retailProductMasterId = retailProductMasterId;
    this.discontiued = discontiued;
    this.trackInventory = trackInventory;
    this.allowNegativeInventory = allowNegativeInventory;
    this.applyTax = applyTax;
    this.applyPromotion = applyPromotion;
  }

  public Long getRetailProductMasterId() {
    return retailProductMasterId;
  }

  public void setRetailProductMasterId(Long retailProductMasterId) {
    this.retailProductMasterId = retailProductMasterId;
  }

  public boolean isDiscontiued() {
    return discontiued;
  }

  public void setDiscontiued(boolean discontiued) {
    this.discontiued = discontiued;
  }

  public boolean isTrackInventory() {
    return trackInventory;
  }

  public void setTrackInventory(boolean trackInventory) {
    this.trackInventory = trackInventory;
  }

  public boolean isAllowNegativeInventory() {
    return allowNegativeInventory;
  }

  public void setAllowNegativeInventory(boolean allowNegativeInventory) {
    this.allowNegativeInventory = allowNegativeInventory;
  }

  public boolean isApplyTax() {
    return applyTax;
  }

  public void setApplyTax(boolean applyTax) {
    this.applyTax = applyTax;
  }

  public boolean isApplyPromotion() {
    return applyPromotion;
  }

  public void setApplyPromotion(boolean applyPromotion) {
    this.applyPromotion = applyPromotion;
  }

}
