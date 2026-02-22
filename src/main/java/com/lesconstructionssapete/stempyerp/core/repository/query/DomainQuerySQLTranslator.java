package com.lesconstructionssapete.stempyerp.core.repository.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.lesconstructionssapete.stempyerp.core.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.core.domain.shared.query.FilterCondition;
import com.lesconstructionssapete.stempyerp.core.domain.shared.query.FilterGroup;
import com.lesconstructionssapete.stempyerp.core.domain.shared.query.FilterNode;
import com.lesconstructionssapete.stempyerp.core.domain.shared.query.SortSpec;
import com.lesconstructionssapete.stempyerp.core.exception.domain.FieldNotFoundException;
import com.lesconstructionssapete.stempyerp.core.shared.query.SqlBuilder;

public class DomainQuerySQLTranslator {

  private final Map<String, String> fieldMap;

  public DomainQuerySQLTranslator(Map<String, String> fieldMap) {
    this.fieldMap = fieldMap;
  }

  record ConditionFragment(String sql, List<?> params) {
  }

  public void apply(SqlBuilder sql, DomainQuery query) {

    if (query.filters() != null) {
      ConditionFragment fragment = buildFragment(query.filters());
      if (!fragment.sql().isBlank()) {
        sql.where(fragment.sql(), fragment.params().toArray());
      }
    }

    applySorting(sql, query);
    applyPagination(sql, query);
  }

  // =============================
  // Recursive builder
  // =============================

  private ConditionFragment buildFragment(FilterNode node) {

    if (node instanceof FilterCondition condition) {
      return buildCondition(condition);
    }

    if (node instanceof FilterGroup group) {

      List<String> sqlParts = new ArrayList<>();
      List<Object> params = new ArrayList<>();

      for (FilterNode child : group.children()) {
        ConditionFragment childFragment = buildFragment(child);

        if (!childFragment.sql().isBlank()) {
          sqlParts.add(childFragment.sql());
          params.addAll(childFragment.params());
        }
      }

      if (sqlParts.isEmpty()) {
        return new ConditionFragment("", Collections.emptyList());
      }

      String joined = String.join(
          " " + group.operator().name() + " ",
          sqlParts);

      return new ConditionFragment(
          "(" + joined + ")",
          params);
    }

    return new ConditionFragment("", Collections.emptyList());
  }

  // =============================
  // Condition builder
  // =============================

  private ConditionFragment buildCondition(FilterCondition c) {

    String column = fieldMap.get(c.field());
    if (column == null) {
      throw new FieldNotFoundException("Invalid field: " + c.field());
    }

    Object value = c.value();

    return switch (c.operator()) {

      case EQUALS ->
        new ConditionFragment(column + " = ?", List.of(value));

      case NOT_EQUALS ->
        new ConditionFragment(column + " <> ?", List.of(value));

      case GREATER_THAN ->
        new ConditionFragment(column + " > ?", List.of(value));

      case LESS_THAN ->
        new ConditionFragment(column + " < ?", List.of(value));

      case GREATER_OR_EQUAL ->
        new ConditionFragment(column + " >= ?", List.of(value));

      case LESS_OR_EQUAL ->
        new ConditionFragment(column + " <= ?", List.of(value));

      case LIKE ->
        new ConditionFragment(
            column + " LIKE ?",
            List.of("%" + value + "%"));

      case IN -> {
        List<?> values = value instanceof List<?> list
            ? list
            : List.of(value);

        if (values.isEmpty()) {
          yield new ConditionFragment("1 = 0", Collections.emptyList());
        }

        String placeholders = values.stream()
            .map(v -> "?")
            .collect(Collectors.joining(", "));

        yield new ConditionFragment(
            column + " IN (" + placeholders + ")",
            new ArrayList<>(values));
      }

      case IS_NULL ->
        new ConditionFragment(column + " IS NULL", Collections.emptyList());

      case IS_NOT_NULL ->
        new ConditionFragment(column + " IS NOT NULL", Collections.emptyList());

      default ->
        throw new UnsupportedOperationException("Unsupported operator: " + c.operator());
    };
  }

  // =============================
  // Sorting
  // =============================

  private void applySorting(SqlBuilder sql, DomainQuery query) {

    if (query.sortSpec() == null)
      return;

    for (SortSpec sort : query.sortSpec()) {

      String column = fieldMap.get(sort.field());
      if (column == null) {
        throw new FieldNotFoundException("Invalid sort field: " + sort.field());
      }

      sql.orderBy(column + (sort.ascending() ? " ASC" : " DESC"));
    }
  }

  // =============================
  // Pagination
  // =============================

  private void applyPagination(SqlBuilder sql, DomainQuery query) {

    if (query.pageSpec() == null)
      return;

    int size = query.pageSpec().size();
    int page = query.pageSpec().page();

    if (size <= 0)
      return;

    int offset = page * size;

    sql.limit(size);
    sql.offset(offset);
  }
}