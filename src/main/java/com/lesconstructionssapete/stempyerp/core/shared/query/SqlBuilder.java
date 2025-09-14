package com.lesconstructionssapete.stempyerp.core.shared.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.shared.util.StringUtil;

/*
 * A lightweight SQL builder that can dynamically append or replace
 * query fragments (WHERE, JOIN, ORDER BY, LIMIT, etc.).
 * 
 * Usage Example:

    // Get base SQL String
    String base = Query.RETAIL_PRODUCT_SELECT_BASE;

    // Build full SQL String with clauses, joins, etc, and append values
    SqlBuilder builder = new SqlBuilder(base)
        .where("rp.product_no = ?", productNo)
        .and("rp.is_enabled = ?", true)
        .orderBy("rp.created_at DESC")
        .limit(10);

    // Build final SQL String and get parameters in a List
    String sql = builder.build();
    List<Object> params = builder.getParams();

    try (var stmt = con.prepareStatement(sql)) {

        // Loop trough parameters and append them to the final SQL String
        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i)); // handles String, int, boolean, etc.
        }
        try (var rs = stmt.executeQuery()) {
            ...
        }
    }

 *
 * Supports placeholders in base queries, e.g.:
 *

    SELECT
      rp.product_no,
      ...
    FROM
      retail_products AS rp
    \*WHERE*\
    \*ORDERBY*\

 * The builder will replace these tokens IF found, otherwise
 * append the clauses at the end.
 */

public class SqlBuilder {
  private final String base;
  private final List<String> whereClauses = new ArrayList<>();
  private final List<Object> params = new ArrayList<>();
  private final List<String> orderByClauses = new ArrayList<>();
  private final List<String> joinClauses = new ArrayList<>();
  private String groupByClause;
  private Integer limit;
  private Integer offset;

  public SqlBuilder(String base) {
    this.base = base.trim();
  }

  // --- WHERE with params ---
  public SqlBuilder where(String condition, Object... values) {
    if (values == null || Arrays.stream(values).anyMatch(v -> v == null)) {
      return this; // Skip if any param is null
    }
    whereClauses.add(condition);
    params.addAll(Arrays.asList(values));
    return this;
  }

  public SqlBuilder and(String condition, Object... values) {
    return where(condition, values);
  }

  // --- JOIN ---
  public SqlBuilder join(String joinClause) {
    joinClauses.add(joinClause);
    return this;
  }

  // --- GROUP BY ---
  public SqlBuilder groupBy(String groupBy) {
    this.groupByClause = groupBy;
    return this;
  }

  // --- ORDER BY ---
  public SqlBuilder orderBy(String orderBy) {
    orderByClauses.add(orderBy);
    return this;
  }

  // --- LIMIT / OFFSET ---
  public SqlBuilder limit(int limit) {
    this.limit = limit;
    return this;
  }

  public SqlBuilder offset(int offset) {
    this.offset = offset;
    return this;
  }

  /**
   * Build final SQL with query fragments
   */
  public String build() {
    String sql = base;

    if (!joinClauses.isEmpty()) {
      sql = StringUtil.replaceOrAppend(
          sql, "/*JOIN*/", String.join(" ", joinClauses));
    }

    if (!whereClauses.isEmpty()) {
      sql = StringUtil.replaceOrAppend(
          sql, "/*WHERE*/", "WHERE " + String.join(" AND ", whereClauses));
    }

    if (groupByClause != null) {
      sql = StringUtil.replaceOrAppend(
          sql, "/*GROUPBY*/", "GROUP BY " + groupByClause);
    }

    if (!orderByClauses.isEmpty()) {
      sql = StringUtil.replaceOrAppend(
          sql, "/*ORDERBY*/", "ORDER BY " + String.join(", ", orderByClauses));
    }

    if (limit != null) {
      sql = StringUtil.replaceOrAppend(
          sql, "/*LIMIT*/", "LIMIT " + limit);
    }

    if (offset != null) {
      sql = StringUtil.replaceOrAppend(
          sql, "/*OFFSET*/", "OFFSET " + offset);
    }

    return StringUtil.normalizeWhitespace(sql);
  }

  // --- Get params ---
  public List<Object> getParams() {
    return params;
  }
}