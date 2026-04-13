package com.lesconstructionssapete.stempyerp.facade.impl.retailproduct;

import java.time.Instant;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.exception.UniqueConstraintException;
import com.lesconstructionssapete.stempyerp.domain.field.retailproduct.RetailProductField;
import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMaster;
import com.lesconstructionssapete.stempyerp.domain.sequence.LiveSequence;
import com.lesconstructionssapete.stempyerp.domain.user.User;
import com.lesconstructionssapete.stempyerp.facade.spi.retailproduct.RetailProductFacade;
import com.lesconstructionssapete.stempyerp.port.transaction.TransactionPropagation;
import com.lesconstructionssapete.stempyerp.port.transaction.TransactionRunner;
import com.lesconstructionssapete.stempyerp.service.numbering.EntityNumberGenerator;
import com.lesconstructionssapete.stempyerp.service.numbering.EntityNumberGeneratorRegistry;
import com.lesconstructionssapete.stempyerp.service.spi.retailproduct.RetailProductService;
import com.lesconstructionssapete.stempyerp.service.spi.sequence.SequenceService;

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

          // Check if the provided retail product number is unique
          // If the product number is null, we skip this check as it will be generated
          // later in the process
          DomainQuery rpvQuery = DomainQuery.builder()
              .where(w -> w.and(
                  c -> c.equals(
                      RetailProductField.RETAIL_PRODUCT_NO, product.getRetailProductNo()),
                  c -> c.isNotNull(
                      RetailProductField.RETAIL_PRODUCT_NO)))
              .build();

          if (!retailProductService.fetch(connection, rpvQuery).isEmpty()) {
            throw new UniqueConstraintException(
                "The retail product '" + product.getRetailProductNo() + "' already exists.");
          }

          // Check if the provided retail product master id exists
          DomainQuery rpmQuery = DomainQuery.builder()
              .where(w -> w.and(
                  c -> c.equals(
                      RetailProductField.RETAIL_PRODUCT_MASTER_ID, product.getRetailProductMasterId())))
              .build();

          if (retailProductService.fetchMasters(connection, rpmQuery).isEmpty()) {
            throw new IllegalArgumentException("Invalid retail product master id provided.");
          }

          String entityNo = product.getRetailProductNo().trim();
          // If the product number is not provided, we generate it using the entity number
          // generator
          if (entityNo == null) {
            LiveSequence liveSequence = sequenceService.next(
                connection,
                product.getEntityName(),
                user.getEntityId());

            EntityNumberGenerator<RetailProduct> RPGenerator = entityGenerators
                .getFor(liveSequence.getEntityType());

            entityNo = RPGenerator.generate(product, liveSequence);
            product.setRetailProductNo(entityNo);
          }

          product.setEntityNo(entityNo);

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
