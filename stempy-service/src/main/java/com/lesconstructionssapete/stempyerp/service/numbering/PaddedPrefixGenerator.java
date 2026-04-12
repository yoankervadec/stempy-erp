package com.lesconstructionssapete.stempyerp.service.numbering;

import com.lesconstructionssapete.stempyerp.generic.GenericEntity;
import com.lesconstructionssapete.stempyerp.sequence.LiveSequence;

public final class PaddedPrefixGenerator
    implements EntityNumberGenerator<GenericEntity> {

  @Override
  public String generate(GenericEntity entity, LiveSequence liveSequence) {

    String formatted = String.format(
        "%0" + liveSequence.getEntityType().getPadLength() + "d",
        liveSequence.getSequenceNo());

    return liveSequence.getEntityType().getPrefix() + formatted;
  }
}
