package com.lesconstructionssapete.stempyerp.domain.shared.query;

import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.shared.query.builder.DomainQueryBuilder;

public record DomainQuery(
    FilterNode filters,
    List<SortSpec> sortSpec,
    PageSpec pageSpec) {

  public static DomainQueryBuilder builder() {
    return new DomainQueryBuilder();
  }

}
