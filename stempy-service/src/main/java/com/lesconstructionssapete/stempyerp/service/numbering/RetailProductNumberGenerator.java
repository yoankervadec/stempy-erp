package com.lesconstructionssapete.stempyerp.service.numbering;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductVariant;
import com.lesconstructionssapete.stempyerp.domain.sequence.LiveSequence;

public final class RetailProductNumberGenerator
    implements EntityNumberGenerator<RetailProductVariant> {

  @Override
  public String generate(RetailProductVariant product, LiveSequence liveSequence) {

    return product.getRetailProductNo();
  }
}
