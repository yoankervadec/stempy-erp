package com.lesconstructionssapete.stempyerp.builder;

import java.util.List;
import java.util.function.Function;

import com.lesconstructionssapete.stempyerp.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.query.FilterNode;
import com.lesconstructionssapete.stempyerp.query.PageSpec;
import com.lesconstructionssapete.stempyerp.query.SortSpec;

public class DomainQueryBuilder {

  private FilterNode filters;
  private List<SortSpec> sortSpec;
  private PageSpec pageSpec;

  public DomainQueryBuilder where(Function<FilterBuilder, FilterNode> fn) {
    this.filters = fn.apply(new FilterBuilder());
    return this;
  }

  public DomainQueryBuilder sort(List<SortSpec> sortSpec) {
    this.sortSpec = sortSpec;
    return this;
  }

  public DomainQueryBuilder page(PageSpec pageSpec) {
    this.pageSpec = pageSpec;
    return this;
  }

  public DomainQuery build() {
    return new DomainQuery(filters, sortSpec, pageSpec);
  }

}
