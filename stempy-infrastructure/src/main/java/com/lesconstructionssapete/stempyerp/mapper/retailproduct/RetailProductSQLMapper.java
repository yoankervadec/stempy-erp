package com.lesconstructionssapete.stempyerp.mapper.retailproduct;

import java.sql.Types;
import java.time.LocalDateTime;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;

public final class RetailProductSQLMapper {

  private RetailProductSQLMapper() {
  }

  public static void bindInsert(SQLBuilder builder, RetailProduct rp) {
    builder
        .bind("", rp.getRetailProductMasterId(), Types.BIGINT)
        .bind("", rp.getRetailProductNo(), Types.VARCHAR)
        .bind("", rp.getRetailProductVariantNo(), Types.VARCHAR)
        .bind("", rp.getName(), Types.VARCHAR)
        .bind("", rp.getDescription(), Types.VARCHAR)
        .bind("", rp.isEnabled(), Types.TINYINT)
        .bind("", rp.getCreatedByUserId(), Types.BIGINT)
        .bind("", rp.getCreatedAt(), Types.TIMESTAMP)
        .bind("", rp.getUpdatedByUserId(), Types.BIGINT)
        .bind("", rp.getUpdatedAt(), Types.TIMESTAMP);
  }

  public static void bindUpdate(SQLBuilder builder, RetailProduct rp) {
    builder
        .bind("", rp.getRetailProductMasterId(), Types.BIGINT)
        .bind("", rp.getRetailProductNo(), Types.VARCHAR)
        .bind("", rp.getRetailProductVariantNo(), Types.VARCHAR)
        .bind("", rp.getName(), Types.VARCHAR)
        .bind("", rp.getDescription(), Types.VARCHAR)
        .bind("", rp.isEnabled(), Types.TINYINT)
        .bind("", LocalDateTime.now(), Types.TIMESTAMP)
        .bind("", rp.getUpdatedByUserId(), Types.BIGINT);
  }

}
