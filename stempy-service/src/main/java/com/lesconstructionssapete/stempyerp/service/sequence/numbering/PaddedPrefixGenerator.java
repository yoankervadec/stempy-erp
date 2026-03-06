package com.lesconstructionssapete.stempyerp.service.sequence.numbering;

import com.lesconstructionssapete.stempyerp.domain.sequence.LiveSequence;
import com.lesconstructionssapete.stempyerp.domain.generic.GenericEntity;

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
