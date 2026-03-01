package com.lesconstructionssapete.stempyerp.app.mapper.base;

import com.lesconstructionssapete.stempyerp.app.dto.base.RetailProductRequest;
import com.lesconstructionssapete.stempyerp.domain.base.retailproduct.RetailProduct;

public class RetailProductMapper {

  public static RetailProduct toDomain(RetailProductRequest dto) {
    return new RetailProduct(
        dto.retailProductId,
        dto.retailProductMasterId,
        dto.retailProductNo,
        dto.retailProductVariantNo,
        dto.name,
        dto.description,
        dto.enabled,
        dto.createdAt,
        dto.createdByUserId,
        dto.updatedAt,
        dto.updatedByUserId);
  }
}
