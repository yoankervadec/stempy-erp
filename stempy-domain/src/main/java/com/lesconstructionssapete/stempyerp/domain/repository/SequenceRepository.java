package com.lesconstructionssapete.stempyerp.domain.repository;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.constant.DomainEntityType;
import com.lesconstructionssapete.stempyerp.domain.sequence.LiveSequence;

public interface SequenceRepository {

  LiveSequence generateFor(
      Connection connection,
      DomainEntityType entityType,
      long createdByUserId) throws SQLException;

}
