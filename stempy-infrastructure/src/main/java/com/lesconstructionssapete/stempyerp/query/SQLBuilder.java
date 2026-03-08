package com.lesconstructionssapete.stempyerp.query;

import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lesconstructionssapete.stempyerp.mapper.SQLField;
import com.lesconstructionssapete.stempyerp.util.StringUtil;

/**
 * A simple SQL builder that supports:
 * - Base SQL with placeholders
 * - Adding WHERE, JOIN, GROUP BY, HAVING, ORDER BY clauses
 * - Binding named parameters (e.g. ":name") with automatic type inference
 * - Generating final SQL with ? placeholders and ordered params list
 * - Debugging with inlined params for logging/testing
 */
public class SQLBuilder {

  /**
   * Represents a single SQL parameter with name, value and JDBC type.
   * The constructor with just name and value will attempt to guess the SQL type
   * based on the Java type of the value.
   */
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

  /**
   * Bind a named parameter. The value's SQL type will be guessed based on its
   * Java type.
   */
  public SQLBuilder bind(String name, Object value) {
    params.put(name, new SQLParam(name, value));
    return this;
  }

  /**
   * Bind a named parameter with a specific SQL type.
   */
  public SQLBuilder bind(String name, Object value, int sqlType) {
    params.put(name, new SQLParam(name, value, sqlType));
    return this;
  }

  /**
   * Bind a parameter using an SQLField definition. The field's logical name is
   * used as the parameter name, and its SQL type is used for binding.
   */
  public SQLBuilder bind(SQLField field, Object value) {
    return bind(field.logicalName(), value, field.sqlType());
  }

  /**
   * Add a WHERE clause.
   */
  public SQLBuilder where(String condition) {
    whereClauses.add(condition);
    return this;
  }

  /**
   * Add an AND condition to the WHERE clause.
   */
  public SQLBuilder and(String condition) {
    return where(condition);
  }

  /**
   * Add a JOIN clause.
   */
  public SQLBuilder join(String joinClause) {
    joinClauses.add(joinClause);
    return this;
  }

  /**
   * Add a GROUP BY clause.
   */
  public SQLBuilder groupBy(String groupBy) {
    this.groupByClause = groupBy;
    return this;
  }

  /**
   * Add a HAVING clause.
   */
  public SQLBuilder having(String having) {
    this.havingClause = having;
    return this;
  }

  /**
   * Add an ORDER BY clause.
   */
  public SQLBuilder orderBy(String orderBy) {
    orderByClauses.add(orderBy);
    return this;
  }

  /**
   * Add a LIMIT clause.
   */
  public SQLBuilder limit(int limit) {
    this.limit = limit;
    return this;
  }

  /**
   * Add an OFFSET clause.
   */
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

  /**
   * Get the list of SQL parameters in the order they appear in the final SQL.
   */
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

  /**
   * Internal method to replace named parameters with ? and track their order.
   */
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