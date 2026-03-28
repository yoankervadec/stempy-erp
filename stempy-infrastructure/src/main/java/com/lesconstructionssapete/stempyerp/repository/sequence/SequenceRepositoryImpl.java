package com.lesconstructionssapete.stempyerp.repository.sequence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.constant.DomainEntityType;
import com.lesconstructionssapete.stempyerp.domain.sequence.LiveSequence;
import com.lesconstructionssapete.stempyerp.exception.SequenceNotFoundException;
import com.lesconstructionssapete.stempyerp.exception.SequenceUpdateException;
import com.lesconstructionssapete.stempyerp.field.sequence.SequenceField;
import com.lesconstructionssapete.stempyerp.field.sequence.SequenceSQLField;
import com.lesconstructionssapete.stempyerp.query.Query;
import com.lesconstructionssapete.stempyerp.query.QueryCache;
import com.lesconstructionssapete.stempyerp.query.SQLBinder;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;
import com.lesconstructionssapete.stempyerp.repository.SequenceRepository;

/*
 * Returns LiveSequence and it's next value for a given EntityType then updates it (++1).
 */

public class SequenceRepositoryImpl implements SequenceRepository {

  @Override
  public LiveSequence generateFor(
      Connection connection,
      DomainEntityType entityType,
      long createdByUserSeq) throws SQLException {

    LiveSequence entitySequence;

    // 1. SELECT ... FOR UPDATE (locks row until commit/rollback)
    String sql = QueryCache.get(
        Query.SELECT_FOR_UPDATE_CORE_DOMAIN_ENTITY_SEQUENCE);

    SQLBuilder selectBuilder = new SQLBuilder(sql)
        .where(SequenceSQLField.get(SequenceField.DOMAIN_ENTITY_ID), "= :entityTypeId")
        .and(SequenceSQLField.get(SequenceField.ENABLED), "= :enabled")
        .bind(SequenceSQLField.get(SequenceField.DOMAIN_ENTITY_ID), entityType.getId())
        .bind(SequenceSQLField.get(SequenceField.ENABLED), true);

    String sqlFinal = selectBuilder.build();
    List<SQLBuilder.SQLParam> params = selectBuilder.getParams();

    try (var stmt = connection.prepareStatement(sqlFinal)) {

      SQLBinder.bind(stmt, params);

      try (var rs = stmt.executeQuery()) {
        if (!rs.next()) {
          throw new SequenceNotFoundException(entityType.getName());
        }
        entitySequence = new LiveSequence(
            entityType,
            rs.getLong("next"),
            createdByUserSeq);
      }
    }

    // 2. UPDATE next
    sql = QueryCache.get(Query.UPDATE_CORE_DOMAIN_ENTITY_SEQUENCE);

    SQLBuilder updateBuilder = new SQLBuilder(sql)
        .where(SequenceSQLField.get(SequenceField.DOMAIN_ENTITY_ID), "= :entityTypeId")
        .and(SequenceSQLField.get(SequenceField.ENABLED), "= :enabled")
        .bind(SequenceSQLField.get(SequenceField.DOMAIN_ENTITY_ID), entityType.getId())
        .bind(SequenceSQLField.get(SequenceField.ENABLED), true);

    sqlFinal = updateBuilder.build();
    params = updateBuilder.getParams();

    try (var stmt = connection.prepareStatement(sqlFinal)) {

      SQLBinder.bind(stmt, params);

      int rows = stmt.executeUpdate();
      if (rows == 0) {
        throw new SequenceUpdateException(entityType.getName(), new SQLException());
      }
    }

    // 3. Return the new value
    return entitySequence;
  }
}
