package com.lesconstructionssapete.stempyerp.domain.field;

import java.util.HashMap;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.domain.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.domain.automation.Job;
import com.lesconstructionssapete.stempyerp.domain.automation.JobLog;
import com.lesconstructionssapete.stempyerp.domain.exception.FieldNotFoundException;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMaster;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMasterPolicy;
import com.lesconstructionssapete.stempyerp.domain.sequence.LiveSequence;
import com.lesconstructionssapete.stempyerp.domain.user.User;

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

  private final Map<String, EntityField> fields = new HashMap<>();

  public DefaultDomainFieldResolver() {
    register(AuthToken.Fields.class);
    register(UserCredential.Fields.class);
    register(Job.Fields.class);
    register(JobLog.Fields.class);
    register(RetailProduct.Fields.class);
    register(RetailProductMaster.Fields.class);
    register(RetailProductMasterPolicy.Fields.class);
    register(LiveSequence.Fields.class);
    register(User.Fields.class);

  }

  /**
   * Registers all constants from the given enum class into the resolver.
   * The enum class must implement the {@link EntityField} interface.
   * Each constant's logical name is used as the key for resolution.
   * If a duplicate logical name is detected, an exception is thrown to prevent
   * conflicts.
   * 
   * @param <T>   The type of the enum, which must implement
   *              {@link EntityField}.
   * @param clazz The class of the enum to register.
   * @throws IllegalStateException    if a duplicate logical name is detected.
   * @throws IllegalArgumentException if the provided class is not an enum or does
   *                                  not implement {@link EntityField}.
   */
  private <E extends Enum<E> & EntityField> void register(Class<E> fields) {

    for (E constant : fields.getEnumConstants()) {
      if (this.fields.containsKey(constant.qualifiedLogicalName())) {
        throw new IllegalStateException(
            "Duplicate logical name detected: " + constant.qualifiedLogicalName() +
                " in " + constant.getClass().getName() + " and " +
                this.fields.get(constant.qualifiedLogicalName()).getClass().getName());
      }

      this.fields.put(constant.qualifiedLogicalName(), constant);
    }
  }

  /**
   * Resolves a field name to its corresponding {@link EntityField}
   * instance.
   * If the field name is not registered, a {@link FieldNotFoundException} is
   * thrown.
   * 
   * @param qualifiedLogicalName The logical name of the field to resolve.
   * @return The corresponding {@link EntityField} instance.
   * @throws FieldNotFoundException if the field name is not registered.
   */
  @Override
  public EntityField resolve(String qualifiedLogicalName) {

    if (qualifiedLogicalName == null || qualifiedLogicalName.trim().isEmpty()) {
      throw new FieldNotFoundException("The field name cannot be null or empty.");
    }

    EntityField field = fields.get(qualifiedLogicalName);

    if (field == null) {
      throw new FieldNotFoundException(
          "The field '" + qualifiedLogicalName + "' is not supported for filtering or sorting.");
    }

    return field;
  }

}
