package com.lesconstructionssapete.stempyerp.infrastructure.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.exception.FieldNotFoundException;
import com.lesconstructionssapete.stempyerp.field.DomainField;
import com.lesconstructionssapete.stempyerp.infrastructure.exception.IllegalFieldException;
import com.lesconstructionssapete.stempyerp.infrastructure.field.SQLField;
import com.lesconstructionssapete.stempyerp.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.query.FilterCondition;
import com.lesconstructionssapete.stempyerp.query.FilterGroup;
import com.lesconstructionssapete.stempyerp.query.FilterNode;
import com.lesconstructionssapete.stempyerp.query.SortSpec;

/**
 * Translates a {@link DomainQuery} into SQL using {@link SQLBuilder}.
 * - Uses a field map to convert logical field names to DB column names
 * - Recursively builds WHERE clause from filter tree
 * - Applies sorting and pagination
 * - Throws {@link FieldNotFoundException} for invalid fields
 */
public final class DomainQuerySQLTranslator {

  private final Map<DomainField, SQLField> fieldMap;

  public DomainQuerySQLTranslator(Map<DomainField, SQLField> fieldMap) {
    this.fieldMap = fieldMap;
  }

  record ConditionFragment(String sql) {
  }

  private int paramCounter = 0;

  private String nextParam() {
    return "p" + (++paramCounter);
  }

  /**
   * Main entry point to apply a DomainQuery to a SqlBuilder. Modifies the builder
   * in place.
   * Handles filters, sorting, and pagination.
   * Throws FieldNotFoundException if any field in filters or sorting is not in
   * the field map.
   * 
   * @param builder The SqlBuilder to modify
   * @param query   The DomainQuery containing filters, sorting, and pagination
   *                info
   * @throws FieldNotFoundException if any field in filters or sorting is not
   *                                found in the field map
   */
  public void apply(SQLBuilder builder, DomainQuery query) {

    // TODO: avoid UPDATE without WHERE if query is null
    if (query == null)
      return;

    if (query.filters() != null) {
      ConditionFragment fragment = buildFragment(builder, query.filters());
      if (!fragment.sql().isBlank()) {
        builder.where(fragment.sql());
      }
    }

    applySorting(builder, query);
    applyPagination(builder, query);
  }

  // =============================
  // Recursive builder
  // =============================

  private ConditionFragment buildFragment(SQLBuilder builder, FilterNode node) {

    if (node instanceof FilterCondition condition) {
      return buildCondition(builder, condition);
    }

    if (node instanceof FilterGroup group) {

      List<String> sqlParts = new ArrayList<>();

      for (FilterNode child : group.children()) {

        ConditionFragment childFragment = buildFragment(builder, child);

        if (!childFragment.sql().isBlank()) {
          sqlParts.add(childFragment.sql());
        }
      }

      if (sqlParts.isEmpty()) {
        return new ConditionFragment("");
      }

      String joined = String.join(
          " " + group.operator().name() + " ",
          sqlParts);

      return new ConditionFragment("(" + joined + ")");
    }

    return new ConditionFragment("");
  }

  // =============================
  // Condition builder
  // =============================

  private ConditionFragment buildCondition(SQLBuilder builder, FilterCondition c) {

    SQLField field = fieldMap.get(c.field());

    if (field == null) {
      throw new IllegalFieldException("Illegal field: " + c.field() + " (" + c.field().logicalName() + ")");
    }

    String column = field.qualifiedColumnName();
    Object value = c.value();

    return switch (c.operator()) {

      case EQUALS -> {
        String p = nextParam();
        builder.bind(p, value, field.sqlType());
        yield new ConditionFragment(column + " = :" + p);
      }

      case NOT_EQUALS -> {
        String p = nextParam();
        builder.bind(p, value, field.sqlType());
        yield new ConditionFragment(column + " <> :" + p);
      }

      case GREATER_THAN -> {
        String p = nextParam();
        builder.bind(p, value, field.sqlType());
        yield new ConditionFragment(column + " > :" + p);
      }

      case LESS_THAN -> {
        String p = nextParam();
        builder.bind(p, value, field.sqlType());
        yield new ConditionFragment(column + " < :" + p);
      }

      case GREATER_OR_EQUAL -> {
        String p = nextParam();
        builder.bind(p, value, field.sqlType());
        yield new ConditionFragment(column + " >= :" + p);
      }

      case LESS_OR_EQUAL -> {
        String p = nextParam();
        builder.bind(p, value, field.sqlType());
        yield new ConditionFragment(column + " <= :" + p);
      }

      case LIKE -> {
        String p = nextParam();
        builder.bind(p, "%" + value + "%", field.sqlType());
        yield new ConditionFragment(column + " LIKE :" + p);
      }

      case IN -> {

        List<?> values = value instanceof List<?> list
            ? list
            : List.of(value);

        if (values.isEmpty()) {
          yield new ConditionFragment("1 = 0");
        }

        List<String> placeholders = new ArrayList<>();

        for (Object v : values) {

          String p = nextParam();

          builder.bind(p, v, field.sqlType());

          placeholders.add(":" + p);
        }

        yield new ConditionFragment(
            column + " IN (" + String.join(", ", placeholders) + ")");
      }

      case IS_NULL ->
        new ConditionFragment(column + " IS NULL");

      case IS_NOT_NULL ->
        new ConditionFragment(column + " IS NOT NULL");

      default ->
        throw new UnsupportedOperationException("Unsupported operator: " + c.operator());
    };
  }

  // =============================
  // Sorting
  // =============================

  private void applySorting(SQLBuilder builder, DomainQuery query) {

    if (query.sortSpec() == null)
      return;

    for (SortSpec sort : query.sortSpec()) {

      SQLField field = fieldMap.get(sort.field());
      if (field == null) {
        throw new IllegalFieldException("Illegal sort field: " + sort.field());
      }
      String column = field.qualifiedColumnName();

      builder.orderBy(column + (sort.ascending() ? " ASC" : " DESC"));
    }
  }

  // =============================
  // Pagination
  // =============================

  private void applyPagination(SQLBuilder builder, DomainQuery query) {

    if (query.pageSpec() == null)
      return;

    int size = query.pageSpec().size();
    int page = query.pageSpec().page();

    if (size <= 0)
      return;

    int offset = page * size;

    builder.limit(size);
    builder.offset(offset);
  }
}