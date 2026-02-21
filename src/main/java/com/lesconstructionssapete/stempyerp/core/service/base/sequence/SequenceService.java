package com.lesconstructionssapete.stempyerp.core.service.base.sequence;

import java.sql.Connection;

import com.lesconstructionssapete.stempyerp.core.domain.base.sequence.LiveSequence;

public interface SequenceService {

  LiveSequence next(
      Connection connection,
      String entityType,
      long createdByUserSeq);

}
