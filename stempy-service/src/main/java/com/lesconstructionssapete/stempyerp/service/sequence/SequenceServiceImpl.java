package com.lesconstructionssapete.stempyerp.service.sequence;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.constant.ConstantUtil;
import com.lesconstructionssapete.stempyerp.constant.RedisConstantCache;
import com.lesconstructionssapete.stempyerp.domain.constant.DomainEntityType;
import com.lesconstructionssapete.stempyerp.domain.sequence.LiveSequence;
import com.lesconstructionssapete.stempyerp.repository.SequenceRepository;

public class SequenceServiceImpl implements SequenceService {

  private final SequenceRepository repository;
  private final RedisConstantCache constantCache;

  public SequenceServiceImpl(SequenceRepository repository, RedisConstantCache constantCache) {
    this.repository = repository;
    this.constantCache = constantCache;
  }

  @Override
  public LiveSequence next(Connection connection, String entityType, long createdByUserSeq) {

    DomainEntityType entity = ConstantUtil.findByName(
        constantCache.getDomainEntityTypes(), entityType);

    try {
      LiveSequence liveSequence = repository.generateFor(connection, entity, createdByUserSeq);

      return liveSequence;
    } catch (SQLException e) {
      throw new RuntimeException("Failed to generate sequence for entity: " + entityType, e);
    }

  }
}
