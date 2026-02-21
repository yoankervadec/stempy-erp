package com.lesconstructionssapete.stempyerp.core.service.base.sequence.numbering;

import java.util.HashMap;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.core.domain.base.constant.EntityType;
import com.lesconstructionssapete.stempyerp.core.domain.base.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.core.domain.generic.GenericEntity;

public class DefaultEntityNumberGeneratorRegistry
    implements EntityNumberGeneratorRegistry {

  private final Map<String, EntityNumberGenerator<? extends GenericEntity>> generators;
  private final EntityNumberGenerator<GenericEntity> defaultGenerator;

  public DefaultEntityNumberGeneratorRegistry() {

    this.generators = new HashMap<>();
    this.defaultGenerator = new PaddedPrefixGenerator();

    registerDefaults();
  }

  private void registerDefaults() {

    generators.put(RetailProduct.RETAIL_PRODUCT_ENTITY_NAME, new RetailProductNumberGenerator());
    // Register other default generators for different EntityTypes as needed
  }

  @SuppressWarnings("unchecked")
  @Override
  public EntityNumberGenerator<? extends GenericEntity> getFor(EntityType entityType) {
    EntityNumberGenerator<? extends GenericEntity> generator = generators.getOrDefault(
        entityType.getName(),
        defaultGenerator);

    return generator;
  }

}