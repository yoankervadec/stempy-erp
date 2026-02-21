package com.lesconstructionssapete.stempyerp.core.service.base.sequence.numbering;

import com.lesconstructionssapete.stempyerp.core.domain.base.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.core.domain.base.sequence.LiveSequence;

public final class RetailProductNumberGenerator
    implements EntityNumberGenerator<RetailProduct> {

  @Override
  public String generate(RetailProduct product, LiveSequence liveSequence) {

    return product.getProductNo();
  }
}
