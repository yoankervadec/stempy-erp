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

          Permission[] permissions = resolvePermissions(target, method);

          if (permissions != null) {
            checkPermissions(permissions);
          }

          return method.invoke(target, args);
        });
  }

  private Permission[] resolvePermissions(Object target, Method method) {

    Class<?> targetClass = target.getClass();

    // 1. Implementation method
    try {
      Method implMethod = targetClass.getMethod(
          method.getName(),
          method.getParameterTypes());

      Permission[] permissions = extractPermissions(implMethod);
      if (permissions != null) {
        return permissions;
      }
    } catch (NoSuchMethodException | SecurityException e) {
      // Method not found in the implementation class, continue to check the interface
    }

    // 2. Interface method
    Permission[] permissions = extractPermissions(method);
    if (permissions != null) {
      return permissions;
    }

    // 3. Class level annotation
    permissions = extractPermissions(targetClass);
    if (permissions != null) {
      return permissions;
    }

    return null;
  }

  private Permission[] extractPermissions(AnnotatedElement element) {

    // Multiple permissions
    RequirePermissions multiple = element.getAnnotation(RequirePermissions.class);
    if (multiple != null) {
      return multiple.value();
    }

    // Single permission
    Permission single = element.getAnnotation(Permission.class);
    if (single != null) {
      return new Permission[] { single };
    }

    return null;
  }

  private void checkPermissions(Permission[] annotation) {

    Long userId = RequestContext.getUserId(); // Get the user ID from the security context

    for (Permission permission : annotation) {
      AppResource resource = permission.resource();
      AppAction action = permission.action();

      // TODO: Add check and logic for "allRequired" flag
      if (!authorizationService.has(userId, resource, action)) {
        throw new PermissionDeniedException(resource, action);
      }
    }
  }

}
