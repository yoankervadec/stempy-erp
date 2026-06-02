package com.lesconstructionssapete.stempyerp.domain.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MappedField {
  Class<? extends DomainFieldProvider> provider();

  String field();
}
