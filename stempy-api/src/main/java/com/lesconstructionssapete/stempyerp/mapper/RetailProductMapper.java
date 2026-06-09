package com.lesconstructionssapete.stempyerp.mapper;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductVariant;
import com.lesconstructionssapete.stempyerp.dto.RetailProductRequest;

public class RetailProductMapper {

  public static RetailProductVariant toDomain(RetailProductRequest dto) {
    return new RetailProductVariant(
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
