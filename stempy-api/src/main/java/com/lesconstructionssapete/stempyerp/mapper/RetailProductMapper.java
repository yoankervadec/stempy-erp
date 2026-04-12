package com.lesconstructionssapete.stempyerp.mapper;

import com.lesconstructionssapete.stempyerp.dto.RetailProductRequest;
import com.lesconstructionssapete.stempyerp.retailproduct.RetailProduct;

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
