package com.lesconstructionssapete.stempyerp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify that a method or class requires a specific permission
 * to be accessed. The permission is defined by a resource and an action. This
 * annotation can be applied at the method level or at the class level. If
 * applied at the class level, it applies to all methods in the class unless
 * overridden by a method-level annotation.
 * 
 * <strong>Prefer using this annotation on service interfaces</strong> rather
 * than implementations to ensure that permission checks are applied
 * consistently regardless of the implementation used.
 * 
 * Use {@link RequirePermissions} if you need to specify multiple permissions
 * for a method or class.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {

  AppResource resource();

  AppAction action();

}
