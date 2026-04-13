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
 * {@link DomainField} instances.
 * It supports registration of fields from various enums that implement the
 * {@link DomainField} interface.
 * 
 * The resolver is used to translate field names received in HTTP requests
 * (e.g., for filtering and sorting) into the corresponding domain field
 * representations that the repository layer can understand.
 */
public class DefaultDomainFieldResolver implements DomainFieldResolver {

  private final Map<String, DomainField> fields = new HashMap<>();

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
   * The enum class must implement the {@link DomainField} interface.
   * Each constant's logical name is used as the key for resolution.
   * If a duplicate logical name is detected, an exception is thrown to prevent
   * conflicts.
   * 
   * @param <E>       The type of the enum, which must implement DomainField.
   * @param enumClass The class of the enum to register.
   * @throws IllegalStateException    if a duplicate logical name is detected.
   * @throws IllegalArgumentException if the provided class is not an enum or does
   *                                  not implement DomainField.
   */
  private <E extends Enum<E> & DomainField> void register(Class<E> enumClass) {
    for (E constant : enumClass.getEnumConstants()) {
      if (fields.containsKey(constant.logicalName())) {
        throw new IllegalStateException(
            "Duplicate logical name detected: " + constant.logicalName() +
                " in " + enumClass.getName() + " and " +
                fields.get(constant.logicalName()).getClass().getName());
      }

      fields.put(constant.logicalName(), constant);
    }
  }

  /**
   * Resolves a field name to its corresponding {@link DomainField} instance.
   * If the field name is not registered, a {@link FieldNotFoundException} is
   * thrown.
   * 
   * @param fieldName The logical name of the field to resolve.
   * @return The corresponding {@link DomainField} instance.
   * @throws FieldNotFoundException if the field name is not registered.
   */
  @Override
  public DomainField resolve(String fieldName) {
    DomainField field = fields.get(fieldName);

    if (field == null) {
      throw new FieldNotFoundException("The field '" + fieldName + "' is not supported for filtering or sorting.");
    }

    return field;
  }

}
