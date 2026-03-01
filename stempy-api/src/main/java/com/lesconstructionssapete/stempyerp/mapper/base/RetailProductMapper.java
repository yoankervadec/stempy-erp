package com.lesconstructionssapete.stempyerp.mapper.base;

import com.lesconstructionssapete.stempyerp.domain.base.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.dto.base.RetailProductRequest;

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
