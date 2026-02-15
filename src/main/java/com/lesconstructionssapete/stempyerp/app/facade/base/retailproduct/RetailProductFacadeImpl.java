package com.lesconstructionssapete.stempyerp.app.facade.base.retailproduct;

import java.time.LocalDateTime;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.config.db.TransactionManager;
import com.lesconstructionssapete.stempyerp.core.config.db.TransactionPropagation;
import com.lesconstructionssapete.stempyerp.core.domain.base.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.core.domain.base.sequence.LiveSequence;
import com.lesconstructionssapete.stempyerp.core.repository.base.retailproduct.RetailProductRepository;
import com.lesconstructionssapete.stempyerp.core.repository.base.sequence.SequenceRepository;
import com.lesconstructionssapete.stempyerp.core.shared.constant.ConstantCache;
import com.lesconstructionssapete.stempyerp.core.shared.constant.ConstantUtil;

public class RetailProductFacadeImpl implements RetailProductFacade {

  private final RetailProductRepository retailProductRepository;

  public RetailProductFacadeImpl(RetailProductRepository retailProductRepository) {
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
        con -> {

          LiveSequence liveSequence = SequenceRepository.generateNextSequence(
              con,
              ConstantUtil.findByName(
                  ConstantCache.getInstance().getEntityTypes(), "RETAIL PRODUCT"),
              product.getCreatedByUserSeq());

          var rp = new RetailProduct(
              liveSequence.getSequenceNo(),
              liveSequence.getEntityNo(),
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
              .insertRetailProduct(con, rp);

          return result;
        });

  }

}
