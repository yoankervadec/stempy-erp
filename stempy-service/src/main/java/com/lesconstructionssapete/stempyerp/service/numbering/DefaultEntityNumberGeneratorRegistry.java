package com.lesconstructionssapete.stempyerp.service.numbering;

import java.util.HashMap;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.domain.constant.DomainEntityType;
import com.lesconstructionssapete.stempyerp.domain.generic.GenericEntity;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;

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
  public EntityNumberGenerator<? extends GenericEntity> getFor(DomainEntityType entityType) {
    EntityNumberGenerator<? extends GenericEntity> generator = generators.getOrDefault(
        entityType.getName(),
        defaultGenerator);

    return generator;
  }

}