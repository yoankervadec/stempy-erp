package com.lesconstructionssapete.stempyerp.service.impl.authorization;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.lesconstructionssapete.stempyerp.annotation.ApplicationAction;
import com.lesconstructionssapete.stempyerp.annotation.RequirePermission;
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
        (proxy, methods, args) -> {

          RequirePermission annotation = resolvePermission(target, methods);

          if (annotation != null) {
            checkPermissions(annotation);
          }

          return methods.invoke(target, args);
        });
  }

  private RequirePermission resolvePermission(Object target, Method method) {

    Class<?> targetClass = target.getClass();

    // 1. Implementation method
    try {
      Method implMethod = targetClass.getMethod(
          method.getName(),
          method.getParameterTypes());

      RequirePermission annotation = implMethod.getAnnotation(RequirePermission.class);
      if (annotation != null) {
        return annotation;
      }
    } catch (NoSuchMethodException | SecurityException e) {
      // Method not found in the implementation class, continue to check the interface
    }

    // 2. Interface method
    RequirePermission annotation = method.getAnnotation(RequirePermission.class);
    if (annotation != null) {
      return annotation;
    }

    // 3. Class level annotation
    annotation = targetClass.getAnnotation(RequirePermission.class);
    if (annotation != null) {
      return annotation;
    }

    return null;
  }

  private void checkPermissions(RequirePermission annotation) {

    Long userId = RequestContext.getUserId(); // Get the user ID from the security context

    String resource = annotation.resource();
    ApplicationAction action = annotation.action(); // action is an enum

    if (!authorizationService.has(userId, resource, action)) {
      throw new PermissionDeniedException(resource, action);
    }
  }

}
