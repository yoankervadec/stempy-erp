package com.lesconstructionssapete.stempyerp.domain.field;

import com.lesconstructionssapete.stempyerp.annotation.FieldSet;

public final class FieldSetResolver {

  private FieldSetResolver() {
  }

  @SuppressWarnings("unchecked")
  public static <E extends Enum<E> & DomainFieldProvider> Class<E> resolve(Class<?> entityClass) {

    FieldSet annotation = entityClass.getAnnotation(FieldSet.class);

    if (annotation == null) {
      throw new IllegalArgumentException(
          "The class " + entityClass.getName() + " is not annotated with @FieldSet");
    }

    Class<?> fieldSet = annotation.value();

    if (!DomainFieldProvider.class.isAssignableFrom(fieldSet)) {
      throw new IllegalArgumentException(
          "The class " + fieldSet.getName() + " must implement DomainFieldProvider");
    }

    return (Class<E>) fieldSet;
  }

}
