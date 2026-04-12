package com.lesconstructionssapete.stempyerp.repository;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.constant.DomainEntityType;
import com.lesconstructionssapete.stempyerp.sequence.LiveSequence;

public interface SequenceRepository {

  LiveSequence generateFor(
      Connection connection,
      DomainEntityType entityType,
      long createdByUserId) throws SQLException;

}
