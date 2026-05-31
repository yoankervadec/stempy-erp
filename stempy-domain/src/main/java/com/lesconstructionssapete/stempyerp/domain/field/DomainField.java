package com.lesconstructionssapete.stempyerp.domain.field;

public final class DomainField {

  // Core attributes
  public final String entityName;
  public final String logicalName;
  public final Class<?> javaType;

  // Behavioral attributes
  public final boolean isVirtual;
  public final boolean isSortable;
  public final boolean isFilterable;

  // Database mapping attributes
  public final String columnName;
  public final String tableName;
  public final int sqlType;

  public DomainField(
      String entityName,
      String logicalName,
      Class<?> javaType,
      boolean isVirtual,
      boolean isSortable,
      boolean isFilterable,
      String columnName,
      String tableName,
      int sqlType) {
    this.entityName = entityName;
    this.logicalName = logicalName;
    this.javaType = javaType;
    this.isVirtual = isVirtual;
    this.isSortable = isSortable;
    this.isFilterable = isFilterable;
    this.columnName = columnName;
    this.tableName = tableName;
    this.sqlType = sqlType;
  }

  public String qualifiedLogicalName() {
    return entityName + "." + logicalName;
  }

  public String qualifiedColumnName() {
    return tableName + "." + columnName;
  }
}
