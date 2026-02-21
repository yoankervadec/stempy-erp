package com.lesconstructionssapete.stempyerp.core.service.base.sequence.numbering;

import com.lesconstructionssapete.stempyerp.core.domain.base.sequence.LiveSequence;
import com.lesconstructionssapete.stempyerp.core.domain.generic.GenericEntity;

/**
 * Interface for generating entity numbers based on a given entity and its live
 * sequence.
 * Implementations can define custom logic to create formatted entity numbers
 * (e.g., RP000010).
 */
public interface EntityNumberGenerator<T extends GenericEntity> {

  /**
   * Generates an entity number for the given entity and live sequence.
   * The implementation can use the entity's properties and the live sequence
   * to create a formatted entity number (e.g., RP000010).
   */
  String generate(T entity, LiveSequence liveSequence);

}
