package com.lesconstructionssapete.stempyerp.service.impl.authorization;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.lesconstructionssapete.stempyerp.annotation.AppAction;
import com.lesconstructionssapete.stempyerp.annotation.AppResource;
import com.lesconstructionssapete.stempyerp.annotation.Permission;
import com.lesconstructionssapete.stempyerp.annotation.RequirePermissions;
import com.lesconstructionssapete.stempyerp.context.RequestContext;
import com.lesconstructionssapete.stempyerp.domain.exception.PermissionDeniedException;
import com.lesconstructionssapete.stempyerp.service.spi.authorization.AuthorizationService;

public class AuthorizationProxyFactory {

  private final AuthorizationService authorizationService;

  public AuthorizationProxyFactory(AuthorizationService authorizationService) {
    this.authorizationService = authorizationService;
  }

  // Helper class to hold the required permissions and whether all of them are
  // required or just one
  final class RequiredPermissions {
    final Permission[] permissions;
    final boolean allRequired;

    public RequiredPermissions(Permission[] permissions, boolean allRequired) {
      this.permissions = permissions;
      this.allRequired = allRequired;
    }
  }

  /**
   * Creates a proxy instance of the given target object that implements the
   * specified interface. The proxy will intercept method calls and check for
   * the @RequirePermission annotation on the target method or class. If the
   * annotation is present, it will check if the current user has the required
   * permission before allowing the method to execute. If the user does not have
   * the required permission, a PermissionDeniedException will be thrown.
   * 
   * @param <T>    The type of the interface that the target object implements.
   * @param target The target object to be proxied.
   * @param iface  The interface class that the target object implements.
   * @return A proxy instance of the target object that implements the specified
   *         interface.
   * @throws PermissionDeniedException If the user does not have the required
   *                                   permission to execute a method annotated
   *                                   with @RequirePermission.
   */
  @SuppressWarnings("unchecked")
  public <T> T create(T target, Class<T> iface) {
    return (T) Proxy.newProxyInstance(
        iface.getClassLoader(),
        new Class<?>[] { iface },
        (proxy, method, args) -> {

          RequiredPermissions required = resolvePermissions(target, method);

          if (required != null) {
            checkPermissions(required);
          }

          return method.invoke(target, args);
        });
  }

  private RequiredPermissions resolvePermissions(Object target, Method method) {

    Class<?> targetClass = target.getClass();

    // 1. Implementation method
    try {
      Method implMethod = targetClass.getMethod(
          method.getName(),
          method.getParameterTypes());

      RequiredPermissions required = extractPermissions(implMethod);
      if (required != null) {
        return required;
      }
    } catch (NoSuchMethodException | SecurityException e) {
      // Method not found in the implementation class, continue to check the interface
    }

    // 2. Interface method
    RequiredPermissions required = extractPermissions(method);
    if (required != null) {
      return required;
    }

    // 3. Class level annotation
    required = extractPermissions(targetClass);
    if (required != null) {
      return required;
    }

    return null;
  }

  private RequiredPermissions extractPermissions(AnnotatedElement element) {

    // Multiple permissions
    RequirePermissions multiple = element.getAnnotation(RequirePermissions.class);
    if (multiple != null) {
      return new RequiredPermissions(multiple.value(), multiple.allRequired());
    }

    // Single permission
    Permission single = element.getAnnotation(Permission.class);
    if (single != null) {
      return new RequiredPermissions(new Permission[] { single }, true);
    }

    return null;
  }

  private void checkPermissions(RequiredPermissions required) {

    Long userId = RequestContext.getUserId(); // Get the user ID from the request context

    Permission[] permissions = required.permissions;
    boolean allRequired = required.allRequired;

    if (allRequired) {
      for (Permission permission : permissions) {
        AppResource resource = permission.resource();
        AppAction action = permission.action();

        if (!authorizationService.has(userId, resource, action)) {
          throw new PermissionDeniedException(resource, action);
        }
      }

      return; // User has all required permissions, allow access
    }

    for (Permission permission : permissions) {
      AppResource resource = permission.resource();
      AppAction action = permission.action();

      if (authorizationService.has(userId, resource, action)) {
        return; // User has at least one of the required permissions, allow access
      }
    }

    throw new PermissionDeniedException(permissions[0].resource(), permissions[0].action());
  }

}
