package com.lesconstructionssapete.stempyerp.repository;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.base.constant.DomainEntityType;
import com.lesconstructionssapete.stempyerp.domain.base.sequence.LiveSequence;

public interface SequenceRepository {

  LiveSequence generateFor(
      Connection connection,
      DomainEntityType entityType,
      long createdByUserId) throws SQLException;

}
