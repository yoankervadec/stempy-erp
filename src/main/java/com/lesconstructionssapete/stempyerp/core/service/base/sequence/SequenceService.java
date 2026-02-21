package com.lesconstructionssapete.stempyerp.core.service.base.sequence;

import java.sql.Connection;

import com.lesconstructionssapete.stempyerp.core.domain.base.sequence.LiveSequence;

public interface SequenceService {

  /**
   * Returns the next sequence for the specified entity type.
   *
   * <p>
   * The sequence increment is atomic and safe under concurrent access.
   *
   * <p>
   * <strong>Important:</strong>
   * This method acquires database-level locks that are held until the
   * surrounding transaction commits or rolls back. It should therefore
   * be called within short-lived transactions to avoid lock contention
   * and reduced throughput.
   *
   * <p>
   * The caller is responsible for managing transaction boundaries
   * on the provided {@link Connection}.
   *
   * @param connection       active database connection
   * @param entityType       logical entity identifier
   * @param createdByUserSeq identifier of the user requesting the sequence
   * @return the next {@link LiveSequence}
   */
  LiveSequence next(
      Connection connection,
      String entityType,
      long createdByUserSeq);

}
