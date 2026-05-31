package com.lesconstructionssapete.stempyerp.infrastructure.field;

import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldProvider;

public class SQLField {

  private final DomainFieldProvider domainField; // Logical field representation used in the application
  private final String tableName; // DB table name
  private final String columnName; // DB column name
  private final int sqlType;

  public SQLField(DomainFieldProvider domainField, String tableName, String columnName, int sqlType) {
    this.domainField = domainField;
    this.tableName = tableName;
    this.columnName = columnName;
    this.sqlType = sqlType;
  }

  public DomainFieldProvider domainField() {
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
