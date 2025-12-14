package com.lesconstructionssapete.stempyerp.app.mapper.base;

import com.lesconstructionssapete.stempyerp.app.dto.base.RetailProductRequest;
import com.lesconstructionssapete.stempyerp.core.domain.base.retailproduct.RetailProduct;

public class RetailProductMapper {

  public static RetailProduct toDomain(RetailProductRequest dto) {
    return new RetailProduct(
        dto.sequenceNo,
        dto.productNo,
        dto.retailPrice,
        dto.cost,
        dto.description,
        dto.retailCategoryId,
        dto.woodSpecyId,
        dto.productWidth,
        dto.productThickness,
        dto.productLength,
        dto.isEnabled,
        dto.createdAt,
        dto.createdByUserSeq);
  }
}
