package com.lesconstructionssapete.stempyerp.facade.base.retailproduct;

import java.time.LocalDateTime;
import java.util.List;

import com.lesconstructionssapete.stempyerp.config.db.TransactionManager;
import com.lesconstructionssapete.stempyerp.config.db.TransactionPropagation;
import com.lesconstructionssapete.stempyerp.domain.base.auth.User;
import com.lesconstructionssapete.stempyerp.domain.base.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.domain.base.sequence.LiveSequence;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.repository.RetailProductRepository;
import com.lesconstructionssapete.stempyerp.service.base.sequence.SequenceService;
import com.lesconstructionssapete.stempyerp.service.base.sequence.numbering.EntityNumberGenerator;
import com.lesconstructionssapete.stempyerp.service.base.sequence.numbering.EntityNumberGeneratorRegistry;

public class RetailProductFacadeImpl
    implements RetailProductFacade {

  private final SequenceService sequenceService;
  private final RetailProductRepository retailProductRepository;
  private final EntityNumberGeneratorRegistry entityGenerators;

  public RetailProductFacadeImpl(
      SequenceService sequenceService,
      EntityNumberGeneratorRegistry entityGenerators,
      RetailProductRepository retailProductRepository) {
    this.sequenceService = sequenceService;
    this.entityGenerators = entityGenerators;
    this.retailProductRepository = retailProductRepository;
  }

  @Override
  public List<RetailProduct> fetch(DomainQuery query) {

    return TransactionManager.execute(
        TransactionPropagation.REQUIRED,
        con -> {
          return retailProductRepository.fetch(con, query);
        });

  }

  @Override
  public RetailProduct insert(User user, RetailProduct product) {

    return TransactionManager.execute(
        TransactionPropagation.REQUIRED,
        connection -> {

          LiveSequence liveSequence = sequenceService.next(
              connection,
              product.getEntityName(),
              user.getEntityId());

          EntityNumberGenerator<RetailProduct> RPGenerator = entityGenerators
              .getFor(liveSequence.getEntityType());

          String entityNo = RPGenerator.generate(product, liveSequence);

          var rp = new RetailProduct(
              product.getRetailProductId(),
              product.getRetailProductMasterId(),
              entityNo,
              product.getRetailProductVariantNo(),
              product.getName(),
              product.getDescription(),
              product.isEnabled(),
              LocalDateTime.now(),
              liveSequence.getCreatedByUserSeq(),
              null,
              null);

          RetailProduct result = retailProductRepository
              .insert(connection, rp);

          return result;
        });

  }

}
