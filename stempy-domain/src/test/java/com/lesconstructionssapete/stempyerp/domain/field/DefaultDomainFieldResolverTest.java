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

  @Test
  void invalidCaseThrowsException() {
    FieldNotFoundException ex = Assertions.assertThrows(
        FieldNotFoundException.class,
        () -> resolver.resolve("retailProduct.id"));

    Assertions.assertTrue(ex.getMessage().contains("retailProduct.id"));
  }

  @Test
  void invalidFormatThrowsException() {
    FieldNotFoundException ex = Assertions.assertThrows(
        FieldNotFoundException.class,
        () -> resolver.resolve("RetailProduct id"));

    Assertions.assertTrue(ex.getMessage().contains("RetailProduct id"));
  }

  @Test
  void missingDomainThrowsException() {
    FieldNotFoundException ex = Assertions.assertThrows(
        FieldNotFoundException.class,
        () -> resolver.resolve("id"));

    Assertions.assertTrue(ex.getMessage().contains("id"));
  }

  @Test
  void nullFieldThrowsException() {
    FieldNotFoundException ex = Assertions.assertThrows(
        FieldNotFoundException.class,
        () -> resolver.resolve(null));

    Assertions.assertTrue(ex.getMessage().contains("null"));
  }

  @Test
  void emptyFieldThrowsException() {
    FieldNotFoundException ex = Assertions.assertThrows(
        FieldNotFoundException.class,
        () -> resolver.resolve(""));

    Assertions.assertTrue(ex.getMessage().contains("empty"));
  }

  @Test
  void blankFieldThrowsException() {
    FieldNotFoundException ex = Assertions.assertThrows(
        FieldNotFoundException.class,
        () -> resolver.resolve("   "));

    Assertions.assertTrue(ex.getMessage().contains("empty"));
  }
}
