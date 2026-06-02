package com.lesconstructionssapete.stempyerp.infrastructure.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.annotation.FieldSet;
import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldProvider;

public final class GenericRowMapper {

  private GenericRowMapper() {
  }

  public static <T> T map(
      FieldValueMap row,
      Class<T> entityClass) {

    try {
      T entity = entityClass.getDeclaredConstructor().newInstance();

      Map<String, Field> entityFields = buildFieldMap(entityClass);

      FieldSet annotation = entityClass.getAnnotation(FieldSet.class);

      if (annotation == null) {
        throw new IllegalStateException(
            "Missing @FieldSet on " + entityClass.getName());

      }

      Class<? extends Enum<?>> fieldSet = annotation.value();

      for (Enum<?> constant : fieldSet.getEnumConstants()) {
        DomainFieldProvider provider = (DomainFieldProvider) constant;

        DomainField domainField = provider.attribute();

        if (domainField.isVirtual) {
          continue;
        }

        Field javaField = entityFields.get(domainField.logicalName);

        if (javaField == null) {
          continue;
        }

        Object value = row.get(domainField);

        javaField.set(entity, value);
      }

      return entity;

    } catch (IllegalAccessException | IllegalArgumentException | IllegalStateException | InstantiationException
        | NoSuchMethodException | SecurityException | InvocationTargetException e) {
      throw new RuntimeException(
          "Failed to map " + entityClass.getName(),
          e);
    }
  }

  private static Map<String, Field> buildFieldMap(
      Class<?> entityClass) {

    Map<String, Field> map = new HashMap<>();

    for (Field field : entityClass.getDeclaredFields()) {
      field.setAccessible(true);
      map.put(field.getName(), field);
    }

    return map;
  }

}
