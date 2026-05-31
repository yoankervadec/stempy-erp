package com.lesconstructionssapete.stempyerp.domain.field;

import java.util.HashMap;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.domain.exception.FieldNotFoundException;
import com.lesconstructionssapete.stempyerp.domain.field.auth.RefreshTokenField;
import com.lesconstructionssapete.stempyerp.domain.field.auth.UserCredentialField;
import com.lesconstructionssapete.stempyerp.domain.field.automation.JobField;
import com.lesconstructionssapete.stempyerp.domain.field.automation.JobLogField;
import com.lesconstructionssapete.stempyerp.domain.field.retailproduct.RetailProductField;
import com.lesconstructionssapete.stempyerp.domain.field.retailproduct.RetailProductMasterField;
import com.lesconstructionssapete.stempyerp.domain.field.retailproduct.RetailProductMasterPolicyField;
import com.lesconstructionssapete.stempyerp.domain.field.sequence.SequenceField;
import com.lesconstructionssapete.stempyerp.domain.field.user.UserField;

/**
 * Default implementation of {@link DomainFieldResolver}.
 * This class maintains a mapping of logical field names to their corresponding
 * {@link DomainFieldProvider} instances.
 * It supports registration of fields from various enums that implement the
 * {@link DomainFieldProvider} interface.
 * 
 * The resolver is used to translate field names received in HTTP requests
 * (e.g., for filtering and sorting) into the corresponding domain field
 * representations that the repository layer can understand.
 */
public class DefaultDomainFieldResolver implements DomainFieldResolver {

  private final Map<String, DomainFieldProvider> fields = new HashMap<>();

  public DefaultDomainFieldResolver() {
    register(RefreshTokenField.class);
    register(UserCredentialField.class);
    register(JobField.class);
    register(JobLogField.class);
    register(RetailProductField.class);
    register(RetailProductMasterField.class);
    register(RetailProductMasterPolicyField.class);
    register(SequenceField.class);
    register(UserField.class);
  }

  /**
   * Registers all constants from the given enum class into the resolver.
   * The enum class must implement the {@link DomainFieldProvider} interface.
   * Each constant's logical name is used as the key for resolution.
   * If a duplicate logical name is detected, an exception is thrown to prevent
   * conflicts.
   * 
   * @param <E>       The type of the enum, which must implement
   *                  DomainFieldProvider.
   * @param enumClass The class of the enum to register.
   * @throws IllegalStateException    if a duplicate logical name is detected.
   * @throws IllegalArgumentException if the provided class is not an enum or does
   *                                  not implement DomainFieldProvider.
   */
  private <E extends Enum<E> & DomainFieldProvider> void register(Class<E> enumClass) {
    for (E constant : enumClass.getEnumConstants()) {
      if (fields.containsKey(constant.attribute().qualifiedLogicalName())) {
        throw new IllegalStateException(
            "Duplicate logical name detected: " + constant.attribute().qualifiedLogicalName() +
                " in " + enumClass.getName() + " and " +
                fields.get(constant.attribute().qualifiedLogicalName()).getClass().getName());
      }

      fields.put(constant.attribute().qualifiedLogicalName(), constant);
    }
  }

  /**
   * Resolves a field name to its corresponding {@link DomainFieldProvider}
   * instance.
   * If the field name is not registered, a {@link FieldNotFoundException} is
   * thrown.
   * 
   * @param qualifiedLogicalName The logical name of the field to resolve.
   * @return The corresponding {@link DomainFieldProvider} instance.
   * @throws FieldNotFoundException if the field name is not registered.
   */
  @Override
  public DomainFieldProvider resolve(String qualifiedLogicalName) {

    if (qualifiedLogicalName == null || qualifiedLogicalName.trim().isEmpty()) {
      throw new FieldNotFoundException("The field name cannot be null or empty.");
    }

    DomainFieldProvider field = fields.get(qualifiedLogicalName);

    if (field == null) {
      throw new FieldNotFoundException(
          "The field '" + qualifiedLogicalName + "' is not supported for filtering or sorting.");
    }

    return field;
  }

}
