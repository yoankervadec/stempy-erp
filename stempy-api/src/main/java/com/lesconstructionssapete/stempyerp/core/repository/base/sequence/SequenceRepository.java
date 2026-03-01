package com.lesconstructionssapete.stempyerp.core.repository.base.sequence;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.core.domain.base.constant.EntityType;
import com.lesconstructionssapete.stempyerp.core.domain.base.sequence.LiveSequence;

public interface SequenceRepository {

  LiveSequence generateFor(
      Connection connection,
      EntityType entityType,
      long createdByUserSeq) throws SQLException;

}
