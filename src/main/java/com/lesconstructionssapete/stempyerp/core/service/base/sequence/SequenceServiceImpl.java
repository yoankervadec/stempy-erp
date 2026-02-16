package com.lesconstructionssapete.stempyerp.core.service.base.sequence;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.core.domain.base.constant.EntityType;
import com.lesconstructionssapete.stempyerp.core.domain.base.sequence.LiveSequence;
import com.lesconstructionssapete.stempyerp.core.repository.base.sequence.SequenceRepository;
import com.lesconstructionssapete.stempyerp.core.shared.constant.ConstantCache;
import com.lesconstructionssapete.stempyerp.core.shared.constant.ConstantUtil;

public class SequenceServiceImpl implements SequenceService {

  private final SequenceRepository repository;

  public SequenceServiceImpl(SequenceRepository repository) {
    this.repository = repository;
  }

  @Override
  public LiveSequence generateFor(Connection connection, String entityType, long createdByUserSeq) {

    EntityType entity = ConstantUtil.findByName(
        ConstantCache.getInstance().getEntityTypes(), entityType);

    try {
      LiveSequence liveSequence = repository.generateFor(connection, entity, createdByUserSeq);
      return liveSequence;
    } catch (SQLException e) {
      throw new RuntimeException("Failed to generate sequence for entity: " + entityType, e);
    }

  }
}
