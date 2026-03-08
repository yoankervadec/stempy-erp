package com.lesconstructionssapete.stempyerp.mapper.retailproduct;

import java.sql.Types;
import java.time.LocalDateTime;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;

public class RetailProductSqlMapper {

  private RetailProductSqlMapper() {
  }

  public static void bindInsert(SQLBuilder builder, RetailProduct rp) {
    builder
        .bindTyped(rp.getRetailProductMasterId(), Types.BIGINT)
        .bindTyped(rp.getRetailProductNo(), Types.VARCHAR)
        .bindTyped(rp.getRetailProductVariantNo(), Types.VARCHAR)
        .bindTyped(rp.getName(), Types.VARCHAR)
        .bindTyped(rp.getDescription(), Types.VARCHAR)
        .bindTyped(rp.isEnabled(), Types.TINYINT)
        .bindTyped(rp.getCreatedByUserId(), Types.BIGINT)
        .bindTyped(rp.getCreatedAt(), Types.TIMESTAMP)
        .bindTyped(rp.getUpdatedByUserId(), Types.BIGINT)
        .bindTyped(rp.getUpdatedAt(), Types.TIMESTAMP);
  }

  public static void bindUpdate(SQLBuilder builder, RetailProduct rp) {
    builder
        .bindTyped(rp.getRetailProductMasterId(), Types.BIGINT)
        .bindTyped(rp.getRetailProductNo(), Types.VARCHAR)
        .bindTyped(rp.getRetailProductVariantNo(), Types.VARCHAR)
        .bindTyped(rp.getName(), Types.VARCHAR)
        .bindTyped(rp.getDescription(), Types.VARCHAR)
        .bindTyped(rp.isEnabled(), Types.TINYINT)
        .bindTyped(LocalDateTime.now(), Types.TIMESTAMP)
        .bindTyped(rp.getUpdatedByUserId(), Types.BIGINT);
  }

}
