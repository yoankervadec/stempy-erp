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

/**
 * Translates a {@link DomainQuery} into SQL using {@link SqlBuilder}.
 * - Uses a field map to convert logical field names to DB column names
 * - Recursively builds WHERE clause from filter tree
 * - Applies sorting and pagination
 * - Throws {@link FieldNotFoundException} for invalid fields
 */
public final class DomainQuerySQLTranslator {

  private final Map<String, String> fieldMap;

  public DomainQuerySQLTranslator(Map<String, String> fieldMap) {
    this.fieldMap = fieldMap;
  }

  record ConditionFragment(String sql, List<?> params) {
  }

  /**
   * Main entry point to apply a DomainQuery to a SqlBuilder. Modifies the builder
   * in place.
   * Handles filters, sorting, and pagination.
   * Throws FieldNotFoundException if any field in filters or sorting is not in
   * the field map.
   * 
   * @param sqlBuilder The SqlBuilder to modify
   * @param query      The DomainQuery containing filters, sorting, and pagination
   *                   info
   * @throws FieldNotFoundException if any field in filters or sorting is not
   *                                found in the field map
   */
  public void apply(SqlBuilder sqlBuilder, DomainQuery query) {

    if (query.filters() != null) {
      ConditionFragment fragment = buildFragment(query.filters());
      if (!fragment.sql().isBlank()) {
        sqlBuilder.where(fragment.sql(), fragment.params().toArray());
      }
    }

    applySorting(sqlBuilder, query);
    applyPagination(sqlBuilder, query);
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