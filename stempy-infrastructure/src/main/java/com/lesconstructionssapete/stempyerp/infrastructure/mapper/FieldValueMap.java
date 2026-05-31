package com.lesconstructionssapete.stempyerp.infrastructure.mapper;

import java.util.LinkedHashMap;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;

public final class FieldValueMap {

  private final Map<DomainField, Object> values = new LinkedHashMap<>();

  public void put(DomainField field, Object value) {
    values.put(field, value);
  }

  @SuppressWarnings("unchecked")
  public <T> T get(DomainField field) {
    return (T) values.get(field);
  }

  public boolean contains(DomainField field) {
    return values.containsKey(field);
  }

}
