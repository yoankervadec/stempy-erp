package com.lesconstructionssapete.stempyerp.infrastructure.mapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldProvider;
import com.lesconstructionssapete.stempyerp.domain.field.MappedField;

public final class ResultSetMapper<T> {

  private final Class<T> entityClass;

  // TODO: Implement caching of field mappings for better performance
  private final ConcurrentHashMap<Class<?>, List<MappedField>> fieldMappingCache = new ConcurrentHashMap<>();

  public ResultSetMapper(Class<T> entityClass) {
    this.entityClass = entityClass;
  }

  public T mapRow(ResultSet rs) throws Exception {
    T instance = entityClass.getDeclaredConstructor().newInstance();
    mapFields(rs, instance, entityClass);
    return instance;
  }

  public List<T> mapAll(ResultSet rs) throws Exception {
    List<T> results = new ArrayList<>();
    while (rs.next()) {
      results.add(mapRow(rs));
    }
    return results;
  }

  private void mapFields(ResultSet rs, Object instance, Class<?> clazz) throws Exception {
    // Walk up the inheritance chain (handles GenericEntity fields too)
    if (clazz == null || clazz == Object.class)
      return;
    mapFields(rs, instance, clazz.getSuperclass());

    for (Field field : clazz.getDeclaredFields()) {
      MappedField annotation = field.getAnnotation(MappedField.class);
      if (annotation == null)
        continue;

      // Resolve the DomainField from the enum
      DomainFieldProvider enumConstant = resolveEnumConstant(annotation);
      DomainField domainField = enumConstant.attribute();

      // Read from ResultSet using the column name
      Object value = readValue(rs, domainField);

      // Set the field value via reflection
      field.setAccessible(true);
      field.set(instance, value);
    }
  }

  private DomainFieldProvider resolveEnumConstant(MappedField annotation) {
    Class<? extends DomainFieldProvider> providerClass = annotation.provider();
    for (DomainFieldProvider constant : providerClass.getEnumConstants()) {
      if (((Enum<?>) constant).name().equals(annotation.field())) {
        return constant;
      }
    }
    throw new IllegalArgumentException(
        "No enum constant '" + annotation.field() + "' in " + providerClass.getName());
  }

  private Object readValue(ResultSet rs, DomainField domainField) throws Exception {
    String column = domainField.columnName;
    int sqlType = domainField.sqlType;

    if (rs.getObject(column) == null)
      return null;

    return switch (sqlType) {
      case java.sql.Types.BIGINT -> rs.getLong(column);
      case java.sql.Types.INTEGER -> rs.getInt(column);
      case
          java.sql.Types.VARCHAR,
          java.sql.Types.CHAR,
          java.sql.Types.LONGVARCHAR ->
        rs.getString(column);
      case java.sql.Types.BOOLEAN -> rs.getBoolean(column);
      case java.sql.Types.TIMESTAMP -> SQLInstantMapper.read(rs, column);
      case java.sql.Types.DATE -> rs.getDate(column);
      case java.sql.Types.DOUBLE -> rs.getDouble(column);
      case java.sql.Types.DECIMAL,
          java.sql.Types.NUMERIC ->
        rs.getBigDecimal(column);
      default -> rs.getObject(column);
    };
  }

}
