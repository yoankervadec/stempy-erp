package com.lesconstructionssapete.stempyerp.field;

public class SQLField {

  private final DomainField logicalName; // logical field (DomainQuery)
  private final String tableName; // DB table name
  private final String columnName; // DB column name
  private final int sqlType;

  public SQLField(DomainField logicalName, String tableName, String columnName, int sqlType) {
    this.logicalName = logicalName;
    this.tableName = tableName;
    this.columnName = columnName;
    this.sqlType = sqlType;
  }

  public DomainField logicalName() {
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
