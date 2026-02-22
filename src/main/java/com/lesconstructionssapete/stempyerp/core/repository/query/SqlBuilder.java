package com.lesconstructionssapete.stempyerp.core.repository.query;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lesconstructionssapete.stempyerp.core.shared.util.StringUtil;

/**
 * SQL builder:
 * - Named parameters (:paramName) mapped to JDBC ? placeholders
 * - Typed params using SqlParam (value + SQL type)
 * - Buckets for bind params (SET values) vs WHERE params
 * - Debug helper to print SQL with values inlined
 * - Extensible for custom clauses
 */

public class SqlBuilder {

  /** Internal wrapper for typed params */
  public static record SqlParam(Object value, int sqlType) {
    public SqlParam(Object value) {
      this(value, guessSqlType(value));
    }

    private static int guessSqlType(Object v) {
      if (v == null)
        return Types.NULL;
      if (v instanceof String)
        return Types.VARCHAR;
      if (v instanceof Integer)
        return Types.INTEGER;
      if (v instanceof Long)
        return Types.BIGINT;
      if (v instanceof Double)
        return Types.DECIMAL;
      if (v instanceof Boolean)
        return Types.BOOLEAN;
      if (v instanceof java.time.LocalDateTime)
        return Types.TIMESTAMP;
      return Types.OTHER;
    }
  }

  private final String base;

  // Clause holders
  private final List<String> whereClauses = new ArrayList<>();
  private final List<String> orderByClauses = new ArrayList<>();
  private final List<String> joinClauses = new ArrayList<>();
  private String groupByClause;
  private String havingClause;
  private Integer limit;
  private Integer offset;

  // Params seperated into buckets
  private final List<SqlParam> bindParams = new ArrayList<>(); // SET / INSERT values
  private final List<SqlParam> whereParams = new ArrayList<>(); // WHERE values

  // Named param support
  private static final Pattern NAMED_PARAM_PATTERN = Pattern.compile(":(\\w+)");

  public SqlBuilder(String base) {
    this.base = base.trim();
  }

  // --- Bind values (SET, INSERT, etc.) ---
  public SqlBuilder bind(Object... values) {
    for (Object v : values) {
      bindParams.add(new SqlParam(v));
    }
    return this;
  }

  public SqlBuilder bindTyped(Object value, int sqlType) {
    bindParams.add(new SqlParam(value, sqlType));
    return this;
  }

  // --- WHERE with params (supports named params) ---
  public SqlBuilder where(String condition, Object... values) {
    if (values == null || Arrays.stream(values).anyMatch(Objects::isNull)) {
      return this; // skip nulls
    }
    whereClauses.add(condition);
    for (Object v : values) {
      whereParams.add(new SqlParam(v));
    }
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

  // --- GROUP BY / HAVING ---
  public SqlBuilder groupBy(String groupBy) {
    this.groupByClause = groupBy;
    return this;
  }

  public SqlBuilder having(String having) {
    this.havingClause = having;
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
   * Build final SQL string.
   * Replaces named params (:name) with JDBC ? placeholders.
   */
  public String build() {
    String sql = base;

    if (!joinClauses.isEmpty()) {
      sql = StringUtil.replaceOrAppend(sql, "/*JOIN*/", String.join(" ", joinClauses));
    }

    if (!whereClauses.isEmpty()) {
      sql = StringUtil.replaceOrAppend(
          sql, "/*WHERE*/", "WHERE " + String.join(" AND ", whereClauses));
    }

    if (groupByClause != null) {
      sql = StringUtil.replaceOrAppend(sql, "/*GROUPBY*/", "GROUP BY " + groupByClause);
    }

    if (havingClause != null) {
      sql = StringUtil.replaceOrAppend(sql, "/*HAVING*/", "HAVING " + havingClause);
    }

    if (!orderByClauses.isEmpty()) {
      sql = StringUtil.replaceOrAppend(
          sql, "/*ORDERBY*/", "ORDER BY " + String.join(", ", orderByClauses));
    }

    if (limit != null) {
      sql = StringUtil.replaceOrAppend(sql, "/*LIMIT*/", "LIMIT " + limit);
    }

    if (offset != null) {
      sql = StringUtil.replaceOrAppend(sql, "/*OFFSET*/", "OFFSET " + offset);
    }

    // Replace named params with ?
    return normalizeNamedParams(StringUtil.normalizeWhitespace(sql));
  }

  // --- Get params in execution order ---
  public List<SqlParam> getParams() {
    List<SqlParam> all = new ArrayList<>();
    all.addAll(bindParams);
    all.addAll(whereParams);
    return all;
  }

  /**
   * Debug helper: returns SQL with params inlined.
   * For logging/testing only — never use in production execution.
   */
  public String toDebugSql() {
    String sql = build();
    List<SqlParam> ps = getParams();

    for (SqlParam p : ps) {

      Object value = p.value();
      String replacement;

      if (value == null) {
        replacement = "NULL";
      } else if (value instanceof Number) {
        replacement = value.toString();
      } else if (value instanceof Boolean) {
        replacement = value.toString(); // true / false (no quotes)
      } else {
        replacement = "'" + value + "'";
      }

      sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(replacement));
    }

    return sql;
  }

  // --- Internal: replace named params with ? ---
  private String normalizeNamedParams(String sql) {
    Matcher matcher = NAMED_PARAM_PATTERN.matcher(sql);
    StringBuffer sb = new StringBuffer();
    while (matcher.find()) {
      matcher.appendReplacement(sb, "?");
    }
    matcher.appendTail(sb);
    return sb.toString();
  }

}