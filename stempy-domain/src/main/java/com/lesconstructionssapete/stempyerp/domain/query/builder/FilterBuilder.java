package com.lesconstructionssapete.stempyerp.domain.query.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.query.ComparisonOperator;
import com.lesconstructionssapete.stempyerp.domain.query.FilterCondition;
import com.lesconstructionssapete.stempyerp.domain.query.FilterGroup;
import com.lesconstructionssapete.stempyerp.domain.query.FilterNode;
import com.lesconstructionssapete.stempyerp.domain.query.LogicalOperator;

public class FilterBuilder {

  // ---- simple conditions ----

  public FilterNode equals(DomainField field, Object value) {
    return new FilterCondition(field, ComparisonOperator.EQUALS, value);
  }

  public FilterNode equalsIfNotNull(DomainField field, Object value) {
    if (value == null) {
      return null;
    }
    return equals(field, value);
  }

  public FilterNode notEquals(DomainField field, Object value) {
    return new FilterCondition(field, ComparisonOperator.NOT_EQUALS, value);
  }

  public FilterNode greaterThan(DomainField field, Object value) {
    return new FilterCondition(field, ComparisonOperator.GREATER_THAN, value);
  }

  public FilterNode lessThan(DomainField field, Object value) {
    return new FilterCondition(field, ComparisonOperator.LESS_THAN, value);
  }

  public FilterNode isNotNull(DomainField field) {
    return new FilterCondition(field, ComparisonOperator.IS_NOT_NULL, null);
  }

  // ---- grouping ----
  @SafeVarargs
  public final FilterNode and(Function<FilterBuilder, FilterNode>... expressions) {
    return group(LogicalOperator.AND, expressions);
  }

  @SafeVarargs
  public final FilterNode or(Function<FilterBuilder, FilterNode>... expressions) {
    return group(LogicalOperator.OR, expressions);
  }

  private FilterNode group(
      LogicalOperator op,
      Function<FilterBuilder, FilterNode>[] expressions) {

    List<FilterNode> children = new ArrayList<>();

    for (Function<FilterBuilder, FilterNode> expr : expressions) {
      children.add(expr.apply(new FilterBuilder()));
    }

    return new FilterGroup(op, children);
  }

}
