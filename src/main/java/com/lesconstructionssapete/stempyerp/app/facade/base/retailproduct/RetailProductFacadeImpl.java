package com.lesconstructionssapete.stempyerp.app.facade.base.retailproduct;

import java.time.LocalDateTime;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.config.db.TransactionManager;
import com.lesconstructionssapete.stempyerp.core.config.db.TransactionPropagation;
import com.lesconstructionssapete.stempyerp.core.domain.base.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.core.domain.base.sequence.LiveSequence;
import com.lesconstructionssapete.stempyerp.core.repository.base.retailproduct.RetailProductRepository;
import com.lesconstructionssapete.stempyerp.core.service.base.sequence.SequenceService;
import com.lesconstructionssapete.stempyerp.core.service.base.sequence.numbering.EntityNumberGenerator;
import com.lesconstructionssapete.stempyerp.core.service.base.sequence.numbering.EntityNumberGeneratorRegistry;

public class RetailProductFacadeImpl implements RetailProductFacade {

  private final SequenceService sequenceService;
  private final RetailProductRepository retailProductRepository;
  private final EntityNumberGeneratorRegistry entityGenerators;

  public RetailProductFacadeImpl(SequenceService sequenceService,
      EntityNumberGeneratorRegistry entityGenerators, RetailProductRepository retailProductRepository) {
    this.sequenceService = sequenceService;
    this.entityGenerators = entityGenerators;
    this.retailProductRepository = retailProductRepository;
  }

  @Override
  public List<RetailProduct> fetchAllProducts() {

    return TransactionManager.execute(
        TransactionPropagation.REQUIRED,
        con -> {
          return retailProductRepository.fetchAll(con, true);
        });

  }

  @Override
  public RetailProduct insert(RetailProduct product) {

    return TransactionManager.execute(
        TransactionPropagation.REQUIRED,
        connection -> {

          LiveSequence liveSequence = sequenceService.next(
              connection,
              product.getEntityName(),
              product.getCreatedByUserSeq());

          EntityNumberGenerator<RetailProduct> RPGenerator = entityGenerators
              .getFor(liveSequence.getEntityType());

          String entityNo = RPGenerator.generate(product, liveSequence);

          var rp = new RetailProduct(
              liveSequence.getSequenceNo(),
              entityNo,
              product.getRetailPrice(),
              product.getCost(),
              product.getDescription(),
              product.getRetailCategoryId(),
              product.getWoodSpecyId(),
              product.getProductWidth(),
              product.getProductThickness(),
              product.getProductLength(),
              product.isEnabled(),
              LocalDateTime.now(),
              liveSequence.getCreatedByUserSeq());

          RetailProduct result = retailProductRepository
              .insertRetailProduct(connection, rp);

          return result;
        });

  }

}
