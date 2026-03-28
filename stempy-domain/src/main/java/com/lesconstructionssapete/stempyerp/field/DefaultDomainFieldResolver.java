package com.lesconstructionssapete.stempyerp.field;

import java.util.HashMap;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.field.retailproduct.RetailProductField;

public class DefaultDomainFieldResolver implements DomainFieldResolver {

  private final Map<String, DomainField> fields = new HashMap<>();

  public DefaultDomainFieldResolver() {
    registerEnum(RetailProductField.class);
  }

  private <E extends Enum<E> & DomainField> void registerEnum(Class<E> enumClass) {
    for (E constant : enumClass.getEnumConstants()) {
      if (fields.containsKey(constant.logicalName())) {
        throw new IllegalStateException(
            "Duplicate logical name detected: " + constant.logicalName());
      }

      fields.put(constant.logicalName(), constant);
    }
  }

  @Override
  public DomainField resolve(String fieldName) {
    DomainField field = fields.get(fieldName);

    if (field == null) {
      throw new IllegalArgumentException("Unknown field: " + fieldName);
    }

    return field;
  }

}
