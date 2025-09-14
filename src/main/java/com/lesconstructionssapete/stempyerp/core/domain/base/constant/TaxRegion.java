package com.lesconstructionssapete.stempyerp.core.domain.base.constant;

import com.lesconstructionssapete.stempyerp.core.shared.constant.ConstantEntity;

public class TaxRegion implements ConstantEntity {

  private final int id;
  private final String taxRegion;
  private final String regionName;
  private final double gstRate;
  private final double pstRate;
  private final boolean isEnabled;

  public TaxRegion(int id, String taxRegion, String regionName, double gstRate, double pstRate, boolean isEnabled) {
    this.id = id;
    this.taxRegion = taxRegion;
    this.regionName = regionName;
    this.gstRate = gstRate;
    this.pstRate = pstRate;
    this.isEnabled = isEnabled;
  }

  @Override
  public int getId() {
    return id;
  }

  public String getTaxRegion() {
    return taxRegion;
  }

  @Override
  public String getName() {
    return regionName;
  }

  public double getGstRate() {
    return gstRate;
  }

  public double getPstRate() {
    return pstRate;
  }

  @Override
  public boolean isEnabled() {
    return isEnabled;
  }

  public double getTaxRate() {
    return getGstRate() + getPstRate();
  }

}
