package com.lesconstructionssapete.stempyerp.repository;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.base.constant.EntityType;
import com.lesconstructionssapete.stempyerp.domain.base.sequence.LiveSequence;

public interface SequenceRepository {

  LiveSequence generateFor(
      Connection connection,
      EntityType entityType,
      long createdByUserSeq) throws SQLException;

}
