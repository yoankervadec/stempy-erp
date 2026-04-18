package com.lesconstructionssapete.stempyerp.domain.field;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.lesconstructionssapete.stempyerp.domain.exception.FieldNotFoundException;
import com.lesconstructionssapete.stempyerp.domain.field.retailproduct.RetailProductField;

public class DefaultDomainFieldResolverTest {

  static DefaultDomainFieldResolver resolver;

  @BeforeAll
  @SuppressWarnings("unused")
  static void setup() {
    resolver = new DefaultDomainFieldResolver();
  }

  @Test
  void registeredFieldIsResolved() {
    DomainField field = resolver.resolve("RetailProduct.id");
    Assertions.assertEquals(RetailProductField.ID, field);
  }

  @Test
  void unregisteredFieldThrowsException() {
    FieldNotFoundException ex = Assertions.assertThrows(
        FieldNotFoundException.class,
        () -> resolver.resolve("ApplicationPermission.id"));

    Assertions.assertTrue(ex.getMessage().contains("ApplicationPermission.id"));

  }
}
