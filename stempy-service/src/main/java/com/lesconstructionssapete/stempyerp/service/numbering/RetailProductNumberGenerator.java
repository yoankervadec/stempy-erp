package com.lesconstructionssapete.stempyerp.service.numbering;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.domain.sequence.LiveSequence;

public final class RetailProductNumberGenerator
    implements EntityNumberGenerator<RetailProduct> {

  @Override
  public String generate(RetailProduct product, LiveSequence liveSequence) {

    return product.getRetailProductNo();
  }
}
