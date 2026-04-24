package com.lesconstructionssapete.stempyerp.infrastructure.query;

import java.sql.Types;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SQLBuilderTest {

  static String selectSQL = """
        SELECT
          table.id,
          table.name,
          table.created_at
        FROM table
        /*WHERE*/
        /*ORDERBY*/
      """;

  static String selectSQLWithWhere = """
        SELECT
          table.id,
          table.name,
          table.created_at
        FROM table
        WHERE table.name = :name
        /*WHERE*/
        /*ORDERBY*/
      """;

  @Test
  void shouldBuildSimpleWhereClause() {
    SQLBuilder builder = new SQLBuilder(selectSQL);

    String sql = builder
        .where("table.name = :name")
        .build();

    Assertions.assertEquals(
        "SELECT table.id, table.name, table.created_at FROM table WHERE table.name = ? /*ORDERBY*/",
        sql);
  }

  @Test
  void shouldBuildFullQuery() {
    SQLBuilder builder = new SQLBuilder("""
        SELECT * FROM users u
        /*JOIN*/
        /*WHERE*/
        /*ORDERBY*/
        /*LIMIT*/
        """);

    String sql = builder
        .join("JOIN roles r ON r.id = u.role_id")
        .where("u.name = :name")
        .and("u.active = :active")
        .orderBy("u.name ASC")
        .limit(10)
        .build();

    Assertions.assertEquals(
        "SELECT * FROM users u " +
            "JOIN roles r ON r.id = u.role_id " +
            "WHERE u.name = ? AND u.active = ? " +
            "ORDER BY u.name ASC " +
            "LIMIT 10",
        sql);
  }

  @Test
  void shouldOrderParamsBasedOnAppearanceInSql() {
    SQLBuilder builder = new SQLBuilder("""
        SELECT * FROM users
        WHERE name = :name AND age > :age
        """);

    builder
        .bind("age", 30, Types.INTEGER)
        .bind("name", "John", Types.VARCHAR);

    builder.build();

    List<SQLBuilder.SQLParam> params = builder.getParams();

    Assertions.assertEquals(2, params.size());
    Assertions.assertEquals("name", params.get(0).name());
    Assertions.assertEquals("age", params.get(1).name());
  }

  @Test
  void shouldThrowIfParamMissing() {
    SQLBuilder builder = new SQLBuilder("""
        SELECT * FROM users WHERE name = :name
        """);

    builder.build();

    var exception = Assertions.assertThrows(
        IllegalStateException.class,
        builder::getParams);
    Assertions.assertNotNull(exception);
  }

  @Test
  void shouldHandleSameParamMultipleTimes() {
    SQLBuilder builder = new SQLBuilder("""
        SELECT * FROM users
        WHERE first_name = :name OR last_name = :name
        """);

    builder.bind("name", "John", Types.VARCHAR);

    builder.build();

    List<SQLBuilder.SQLParam> params = builder.getParams();

    Assertions.assertEquals(2, params.size());
    Assertions.assertEquals("name", params.get(0).name());
    Assertions.assertEquals("name", params.get(1).name());
  }

  @Test
  void shouldInlineParamsInDebugSql() {
    SQLBuilder builder = new SQLBuilder("""
        SELECT * FROM users WHERE name = :name AND active = :active
        """);

    builder
        .bind("name", "John", Types.VARCHAR)
        .bind("active", true, Types.BOOLEAN);

    String sql = builder.build();
    String debug = builder.toDebugSql(sql);

    Assertions.assertEquals(
        "SELECT * FROM users WHERE name = 'John' AND active = true",
        debug);
  }

  @Test
  void shouldRenderNullCorrectlyInDebugSql() {
    SQLBuilder builder = new SQLBuilder("""
        SELECT * FROM users WHERE deleted_at = :deletedAt
        """);

    builder.bind("deletedAt", null, Types.TIMESTAMP);

    String sql = builder.build();
    String debug = builder.toDebugSql(sql);

    Assertions.assertTrue(debug.contains("deleted_at = NULL"));
  }

  @Test
  void shouldClearParams() {
    SQLBuilder builder = new SQLBuilder("""
        SELECT * FROM users WHERE name = :name
        """);

    builder.bind("name", "John", Types.VARCHAR);
    builder.clearParams();
    builder.build();

    var exception = Assertions.assertThrows(
        IllegalStateException.class,
        builder::getParams);
    Assertions.assertNotNull(exception);
  }

  @Test
  void shouldInferSqlTypes() {
    Assertions.assertEquals(Types.VARCHAR, new SQLBuilder.SQLParam("a", "x").sqlType());
    Assertions.assertEquals(Types.INTEGER, new SQLBuilder.SQLParam("a", 1).sqlType());
    Assertions.assertEquals(Types.BOOLEAN, new SQLBuilder.SQLParam("a", true).sqlType());
    Assertions.assertEquals(Types.NULL, new SQLBuilder.SQLParam("a", null).sqlType());
  }

  @Test
  void shouldRequireWhereClauseForUpdate() {
    SQLBuilder builder = new SQLBuilder("""
        UPDATE users SET active = false
        """);

    var exception = Assertions.assertThrows(
        IllegalStateException.class,
        builder::build);
    Assertions.assertNotNull(exception);
  }

  @Test
  void shouldRequireWhereClauseForUpdateWithComment() {
    SQLBuilder builder = new SQLBuilder("""
        -- will this comment break it?
        UPDATE users SET active = false
        """);

    var exception = Assertions.assertThrows(
        IllegalStateException.class,
        builder::build);
    Assertions.assertNotNull(exception);
  }

  @Test
  void shouldRequireWhereClauseForUpdateWithMultipleComments() {
    SQLBuilder builder = new SQLBuilder("""
        -- will this comment break it?

        -- what about multiple comments?
        UPDATE users SET active = false
        """);

    var exception = Assertions.assertThrows(
        IllegalStateException.class,
        builder::build);
    Assertions.assertNotNull(exception);
  }

  @Test
  void shouldRequireWhereClauseForUpdateWithCommentAndLineBreaks() {
    SQLBuilder builder = new SQLBuilder("""

        -- will this comment with extra line breaks break it?

        UPDATE users SET active = false
        """);

    var exception = Assertions.assertThrows(
        IllegalStateException.class,
        builder::build);
    Assertions.assertNotNull(exception);
  }

  @Test
  void shouldNotRequireWhereClauseForSelect() {
    SQLBuilder builder = new SQLBuilder("""
        SELECT * FROM users
        """);

    Assertions.assertDoesNotThrow(builder::build);
  }
}
