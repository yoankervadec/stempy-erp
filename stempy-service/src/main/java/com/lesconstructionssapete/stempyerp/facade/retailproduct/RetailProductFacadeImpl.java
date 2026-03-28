package com.lesconstructionssapete.stempyerp.facade.retailproduct;

import java.time.Instant;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.User;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMaster;
import com.lesconstructionssapete.stempyerp.domain.sequence.LiveSequence;
import com.lesconstructionssapete.stempyerp.domain.shared.query.ComparisonOperator;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.shared.query.FilterCondition;
import com.lesconstructionssapete.stempyerp.service.retailproduct.RetailProductService;
import com.lesconstructionssapete.stempyerp.service.sequence.SequenceService;
import com.lesconstructionssapete.stempyerp.service.sequence.numbering.EntityNumberGenerator;
import com.lesconstructionssapete.stempyerp.service.sequence.numbering.EntityNumberGeneratorRegistry;
import com.lesconstructionssapete.stempyerp.transaction.TransactionPropagation;
import com.lesconstructionssapete.stempyerp.transaction.TransactionRunner;

public class RetailProductFacadeImpl
    implements RetailProductFacade {

  private final TransactionRunner transaction;
  private final SequenceService sequenceService;
  private final RetailProductService retailProductService;
  private final EntityNumberGeneratorRegistry entityGenerators;

  public RetailProductFacadeImpl(
      TransactionRunner transaction,
      SequenceService sequenceService,
      EntityNumberGeneratorRegistry entityGenerators,
      RetailProductService retailProductService) {
    this.transaction = transaction;
    this.sequenceService = sequenceService;
    this.entityGenerators = entityGenerators;
    this.retailProductService = retailProductService;
  }

  @Override
  public List<RetailProduct> fetch(DomainQuery query) {

    return transaction.execute(
        TransactionPropagation.REQUIRED,
        con -> {
          return retailProductService.fetch(con, query);
        });

  }

  @Override
  public List<RetailProductMaster> fetchMasters(DomainQuery query) {

    return transaction.execute(
        TransactionPropagation.REQUIRED,
        con -> {
          return retailProductService.fetchMasters(con, query);
        });

  }

  @Override
  public RetailProduct insert(User user, RetailProduct product) {

    return transaction.execute(
        TransactionPropagation.REQUIRED,
        connection -> {

          // Check if the provided retail product master id exists
          var rpmQuery = new DomainQuery(
              new FilterCondition(
                  "retailProductMasterId",
                  ComparisonOperator.EQUALS,
                  product.getRetailProductMasterId()),
              null,
              null);

          if (retailProductService.fetchMasters(connection, rpmQuery).isEmpty()) {
            throw new IllegalArgumentException("Invalid retail product master id provided.");
          }

          LiveSequence liveSequence = sequenceService.next(
              connection,
              product.getEntityName(),
              user.getEntityId());

          EntityNumberGenerator<RetailProduct> RPGenerator = entityGenerators
              .getFor(liveSequence.getEntityType());

          product.setEntityNo(RPGenerator.generate(product, liveSequence));

          RetailProduct result = retailProductService
              .insert(connection, product);

          return result;
        });

  }

  @Override
  public RetailProductMaster insertMaster(User user, RetailProductMaster productMaster) {
    return transaction.execute(
        TransactionPropagation.REQUIRED,
        connection -> {

          LiveSequence liveSequence = sequenceService.next(
              connection,
              productMaster.getEntityName(),
              user.getEntityId());

          EntityNumberGenerator<RetailProductMaster> RPMGenerator = entityGenerators
              .getFor(liveSequence.getEntityType());

          String entityNo = RPMGenerator.generate(productMaster, liveSequence);

          var rpm = new RetailProductMaster(
              productMaster.getEntityId(),
              entityNo,
              productMaster.getName(),
              productMaster.getDescription(),
              productMaster.isEnabled(),
              Instant.now(),
              liveSequence.getCreatedByUserSeq(),
              null,
              null,
              null,
              null,
              null);

          RetailProductMaster result = retailProductService
              .insertMaster(connection, rpm);

          return result;
        });
  }

}
