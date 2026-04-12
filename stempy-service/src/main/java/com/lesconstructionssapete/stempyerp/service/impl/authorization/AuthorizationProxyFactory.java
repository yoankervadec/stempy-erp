package com.lesconstructionssapete.stempyerp.service.impl.authorization;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.lesconstructionssapete.stempyerp.annotation.ApplicationAction;
import com.lesconstructionssapete.stempyerp.annotation.RequirePermission;
import com.lesconstructionssapete.stempyerp.context.SecurityContext;
import com.lesconstructionssapete.stempyerp.service.spi.authorization.AuthorizationService;

public class AuthorizationProxyFactory {

  private final AuthorizationService authorizationService;

  public AuthorizationProxyFactory(AuthorizationService authorizationService) {
    this.authorizationService = authorizationService;
  }

  @SuppressWarnings("unchecked")
  public <T> T create(T target, Class<T> iface) {
    return (T) Proxy.newProxyInstance(
        iface.getClassLoader(),
        new Class<?>[] { iface },
        (proxy, methods, args) -> {

          checkPermissions(target, methods);

          return methods.invoke(target, args);
        });
  }

  private void checkPermissions(Object target, Method method) {

    // Check if the method implementation has the RequirePermission annotation
    Method targetMethod;
    try {
      targetMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
    } catch (NoSuchMethodException | SecurityException e) {
      return;
    }
    RequirePermission annotation = targetMethod.getAnnotation(RequirePermission.class);

    // Check if the class has the annotation if the method does not have it
    if (annotation == null) {
      annotation = target.getClass().getAnnotation(RequirePermission.class);
    }

    // If the annotation is present, check if the user has the required permission
    if (annotation != null) {
      Long userId = SecurityContext.getUserId(); // Get the user ID from the security context

      String resource = annotation.resource();
      ApplicationAction action = annotation.action(); // action is an enum

      if (!authorizationService.has(userId, resource, action)) {
        throw new SecurityException(
            "Permission denied for action: " + action + " on resource: " + resource);
      }
    }

  }

}
