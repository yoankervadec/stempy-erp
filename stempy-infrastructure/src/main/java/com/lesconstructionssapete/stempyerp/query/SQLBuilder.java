package com.lesconstructionssapete.stempyerp.query;

import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lesconstructionssapete.stempyerp.util.StringUtil;

/**
 * SQL builder:
 * - Named parameters (:paramName) mapped to JDBC ? placeholders
 * - Typed params using SqlParam (value + SQL type)
 * - Buckets for bind params (SET values) vs WHERE params
 * - Debug helper to print SQL with values inlined
 * - Extensible for custom clauses
 */

public class SQLBuilder {

  /** Internal wrapper for typed params */
  public static record SQLParam(String name, Object value, int sqlType) {
    public SQLParam(String name, Object value) {
      this(name, value, guessSqlType(value));
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

  // Params bucket
  private final Map<String, SQLParam> params = new LinkedHashMap<>();
  private final List<String> paramOrder = new ArrayList<>();

  // Named param support
  private static final Pattern NAMED_PARAM_PATTERN = Pattern.compile(":(\\w+)");

  public SQLBuilder(String base) {
    this.base = base.trim();
  }

  // --- Bind values (SET, INSERT, WHERE etc.) ---
  public SQLBuilder bind(String name, Object value) {
    params.put(name, new SQLParam(name, value));
    return this;
  }

  public SQLBuilder bind(String name, Object value, int sqlType) {
    params.put(name, new SQLParam(name, value, sqlType));
    return this;
  }

  // --- WHERE with params (supports named params) ---
  public SQLBuilder where(String condition) {
    whereClauses.add(condition);
    return this;
  }

  public SQLBuilder and(String condition) {
    return where(condition);
  }

  // --- JOIN ---
  public SQLBuilder join(String joinClause) {
    joinClauses.add(joinClause);
    return this;
  }

  // --- GROUP BY / HAVING ---
  public SQLBuilder groupBy(String groupBy) {
    this.groupByClause = groupBy;
    return this;
  }

  public SQLBuilder having(String having) {
    this.havingClause = having;
    return this;
  }

  // --- ORDER BY ---
  public SQLBuilder orderBy(String orderBy) {
    orderByClauses.add(orderBy);
    return this;
  }

  // --- LIMIT / OFFSET ---
  public SQLBuilder limit(int limit) {
    this.limit = limit;
    return this;
  }

  public SQLBuilder offset(int offset) {
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
  public List<SQLParam> getParams() {

    List<SQLParam> ordered = new ArrayList<>();

    for (String name : paramOrder) {

      SQLParam p = params.get(name);

      if (p == null) {
        throw new IllegalStateException("Missing SQL parameter: " + name);
      }

      ordered.add(p);
    }

    return ordered;
  }

  /**
   * Debug helper: returns SQL with params inlined.
   * For logging/testing only — never use in production execution.
   */
  public String toDebugSql() {
    String sql = build();
    List<SQLParam> ps = getParams();

    for (SQLParam p : ps) {

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

      String paramName = matcher.group(1);
      paramOrder.add(paramName);

      matcher.appendReplacement(sb, "?");
    }

    matcher.appendTail(sb);

    return sb.toString();
  }

}