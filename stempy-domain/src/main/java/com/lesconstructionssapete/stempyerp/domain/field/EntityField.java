package com.lesconstructionssapete.stempyerp.domain.field;

public interface EntityField {

  FieldMeta meta();

  default String qualifiedLogicalName() {
    return meta().getQualifiedLogicalName();
  }

  default String qualifiedColumnName() {
    return meta().getQualifiedColumnName();
  }

  default String entityName() {
    return meta().getEntityName();
  }

  default String logicalName() {
    return meta().getLogicalName();
  }

  default Class<?> javaType() {
    return meta().getJavaType();
  }

  default boolean isVirtual() {
    return meta().isVirtual();
  }

  default boolean isSortable() {
    return meta().isSortable();
  }

  default boolean isFilterable() {
    return meta().isFilterable();
  }

  default boolean isNullable() {
    return meta().isNullable();
  }

  default boolean isInsertable() {
    return meta().isInsertable();
  }

  default boolean isUpdatable() {
    return meta().isUpdatable();
  }

  default String columnName() {
    return meta().getColumnName();
  }

  default String tableName() {
    return meta().getTableName();
  }

  default int sqlType() {
    return meta().getSqlType();
  }
}
