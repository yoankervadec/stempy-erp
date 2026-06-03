package com.lesconstructionssapete.stempyerp.domain.field;

import java.util.Objects;

public final class FieldMeta {

  // Core attributes
  private final String entityName;
  private final String logicalName;
  private final Class<?> javaType;

  // Behavioral attributes
  private final boolean isVirtual;
  private final boolean isSortable;
  private final boolean isFilterable;
  private final boolean isNullable;
  private final boolean isInsertable;
  private final boolean isUpdatable;

  // Database mapping attributes
  private final String columnName;
  private final String tableName;
  private final int sqlType;

  private FieldMeta(Builder builder) {
    this.entityName = Objects.requireNonNull(builder.entityName, "Entity name cannot be null");
    this.logicalName = Objects.requireNonNull(builder.logicalName, "Logical name cannot be null");
    this.javaType = Objects.requireNonNull(builder.javaType, "Java type cannot be null");
    this.isVirtual = builder.isVirtual;
    this.isSortable = builder.isSortable;
    this.isFilterable = builder.isFilterable;
    this.isNullable = builder.isNullable;
    this.isInsertable = builder.isInsertable;
    this.isUpdatable = builder.isUpdatable;
    this.columnName = builder.columnName;
    this.tableName = builder.tableName;
    this.sqlType = builder.sqlType;
  }

  public String getQualifiedLogicalName() {
    return entityName + "." + logicalName;
  }

  public String getQualifiedColumnName() {
    return tableName + "." + columnName;
  }

  public String getEntityName() {
    return entityName;
  }

  public String getLogicalName() {
    return logicalName;
  }

  public Class<?> getJavaType() {
    return javaType;
  }

  public boolean isVirtual() {
    return isVirtual;
  }

  public boolean isSortable() {
    return isSortable;
  }

  public boolean isFilterable() {
    return isFilterable;
  }

  public boolean isNullable() {
    return isNullable;
  }

  public boolean isInsertable() {
    return isInsertable;
  }

  public boolean isUpdatable() {
    return isUpdatable;
  }

  public String getColumnName() {
    return columnName;
  }

  public String getTableName() {
    return tableName;
  }

  public int getSqlType() {
    return sqlType;
  }

  public static Builder builder(String entityName, String tableName) {
    return new Builder(entityName, tableName);
  }

  public static final class Builder {
    // Core attributes
    private final String entityName;
    private String logicalName;
    private Class<?> javaType;

    // Behavioral attributes
    private boolean isVirtual = false;
    private boolean isSortable = true;
    private boolean isFilterable = true;
    private boolean isNullable = true;
    private boolean isInsertable = true;
    private boolean isUpdatable = true;

    // Database mapping attributes
    private String columnName;
    private final String tableName;
    private int sqlType;

    private Builder(String entityName, String tableName) {
      this.entityName = entityName;
      this.tableName = tableName;
    }

    public Builder field(String logicalName, String columnName) {
      this.logicalName = logicalName;
      this.columnName = columnName;
      return this;
    }

    public Builder type(Class<?> javaType, int sqlType) {
      this.javaType = javaType;
      this.sqlType = sqlType;
      return this;
    }

    public Builder virtual() {
      this.isVirtual = true;
      return this;
    }

    public Builder notSortable() {
      this.isSortable = false;
      return this;
    }

    public Builder notFilterable() {
      this.isFilterable = false;
      return this;
    }

    public Builder notNullable() {
      this.isNullable = false;
      return this;
    }

    public Builder notInsertable() {
      this.isInsertable = false;
      return this;
    }

    public Builder notUpdatable() {
      this.isUpdatable = false;
      return this;
    }

    public FieldMeta build() {
      return new FieldMeta(this);
    }

  }

}
