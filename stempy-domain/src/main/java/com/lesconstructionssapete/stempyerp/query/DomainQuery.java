package com.lesconstructionssapete.stempyerp.query;

import java.util.List;

import com.lesconstructionssapete.stempyerp.builder.DomainQueryBuilder;

public record DomainQuery(
    FilterNode filters,
    List<SortSpec> sortSpec,
    PageSpec pageSpec) {

  public static DomainQueryBuilder builder() {
    return new DomainQueryBuilder();
  }

}
