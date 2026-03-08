package com.lesconstructionssapete.stempyerp.mapper;

public class SQLField {

  private final String logicalName; // logical field (DomainQuery)
  private final String tableName; // DB table name
  private final String columnName; // DB column name
  private final int sqlType;

  public SQLField(String logicalName, String tableName, String columnName, int sqlType) {
    this.logicalName = logicalName;
    this.tableName = tableName;
    this.columnName = columnName;
    this.sqlType = sqlType;
  }

  public String logicalName() {
    return logicalName;
  }

  public String tableName() {
    return tableName;
  }

  public String columnName() {
    return columnName;
  }

  public String qualifiedColumnName() {
    return tableName + "." + columnName;
  }

  public int sqlType() {
    return sqlType;
  }

}
