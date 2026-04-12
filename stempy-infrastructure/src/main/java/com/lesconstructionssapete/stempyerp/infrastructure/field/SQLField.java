package com.lesconstructionssapete.stempyerp.infrastructure.field;

import com.lesconstructionssapete.stempyerp.field.DomainField;

public class SQLField {

  private final DomainField domainField; // Logical field representation used in the application
  private final String tableName; // DB table name
  private final String columnName; // DB column name
  private final int sqlType;

  public SQLField(DomainField domainField, String tableName, String columnName, int sqlType) {
    this.domainField = domainField;
    this.tableName = tableName;
    this.columnName = columnName;
    this.sqlType = sqlType;
  }

  public DomainField domainField() {
    return domainField;
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
