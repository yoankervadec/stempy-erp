package com.lesconstructions.sapete.stempyerp.core.repository.base.sequence;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructions.sapete.stempyerp.core.domain.base.constant.EntityType;
import com.lesconstructions.sapete.stempyerp.core.domain.base.sequence.LiveSequence;
import com.lesconstructions.sapete.stempyerp.core.shared.query.Query;
import com.lesconstructions.sapete.stempyerp.core.shared.query.QueryCache;

/*
 * Returns an EntityType and it's next value for a given EntityType then updates it (++1).
 */

public class SequenceRepository {

  public static LiveSequence generateNextSequence(
      Connection con,
      EntityType entityType,
      Long createdByUserSeq) throws SQLException {

    LiveSequence entitySequence;

    // 1. SELECT ... FOR UPDATE (locks row until commit/rollback)
    String selectSql = QueryCache.get(Query.SELECT_NEXT_SEQUENCE_NO);
    try (var stmt = con.prepareStatement(selectSql)) {
      stmt.setInt(1, entityType.getId());
      try (var rs = stmt.executeQuery()) {
        if (!rs.next()) {
          throw new SQLException("No sequence found for entity type: " + entityType);
        }
        entitySequence = new LiveSequence(
            entityType,
            rs.getLong("next_value"),
            createdByUserSeq);
      }
    }

    // 2. UPDATE next_value
    String updateSql = QueryCache.get(Query.UPDATE_NEXT_SEQUENCE_NO);
    try (var stmt = con.prepareStatement(updateSql)) {
      stmt.setInt(1, entityType.getId());
      int rows = stmt.executeUpdate();
      if (rows == 0) {
        throw new SQLException("Failed to update sequence for entity type: " + entityType);
      }
    }

    // 3. Return the new value
    return entitySequence;
  }
}
