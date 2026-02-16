package com.lesconstructionssapete.stempyerp.core.repository.base.sequence;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.core.domain.base.constant.EntityType;
import com.lesconstructionssapete.stempyerp.core.domain.base.sequence.LiveSequence;
import com.lesconstructionssapete.stempyerp.core.exception.domain.SequenceNotFoundException;
import com.lesconstructionssapete.stempyerp.core.exception.internal.SequenceUpdateException;
import com.lesconstructionssapete.stempyerp.core.shared.query.Query;
import com.lesconstructionssapete.stempyerp.core.shared.query.QueryCache;

/*
 * Returns LiveSequence and it's next value for a given EntityType then updates it (++1).
 */

public class SequenceRepositoryImpl implements SequenceRepository {

  @Override
  public LiveSequence generateFor(
      Connection connection,
      EntityType entityType,
      long createdByUserSeq) throws SQLException {

    LiveSequence entitySequence;

    // 1. SELECT ... FOR UPDATE (locks row until commit/rollback)
    String selectSql = QueryCache.get(Query.SELECT_NEXT_SEQUENCE_NO);
    try (var stmt = connection.prepareStatement(selectSql)) {
      stmt.setInt(1, entityType.getId());
      try (var rs = stmt.executeQuery()) {
        if (!rs.next()) {
          throw new SequenceNotFoundException(entityType.getName());
        }
        entitySequence = new LiveSequence(
            entityType,
            rs.getLong("next_value"),
            createdByUserSeq);
      }
    }

    // 2. UPDATE next_value
    String updateSql = QueryCache.get(Query.UPDATE_NEXT_SEQUENCE_NO);
    try (var stmt = connection.prepareStatement(updateSql)) {
      stmt.setInt(1, entityType.getId());
      int rows = stmt.executeUpdate();
      if (rows == 0) {
        throw new SequenceUpdateException(entityType.getName(), new SQLException());
      }
    }

    // 3. Return the new value
    return entitySequence;
  }
}
