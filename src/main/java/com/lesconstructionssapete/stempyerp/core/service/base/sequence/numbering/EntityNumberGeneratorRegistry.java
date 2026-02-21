package com.lesconstructionssapete.stempyerp.core.service.base.sequence.numbering;

import com.lesconstructionssapete.stempyerp.core.domain.base.constant.EntityType;
import com.lesconstructionssapete.stempyerp.core.domain.generic.GenericEntity;

/**
 * Registry to manage EntityNumberGenerators for different EntityTypes.
 * Provides a method to retrieve the appropriate generator based on EntityType.
 */
public interface EntityNumberGeneratorRegistry {

  /**
   * Retrieves the EntityNumberGenerator for the given EntityType.
   * If no specific generator is registered for the EntityType, returns a default
   * generator.
   */
  <T extends GenericEntity> EntityNumberGenerator<T> getFor(EntityType entityType);

}
