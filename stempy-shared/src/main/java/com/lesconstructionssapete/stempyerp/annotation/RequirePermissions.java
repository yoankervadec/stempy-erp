package com.lesconstructionssapete.stempyerp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify that a method or class requires multiple permissions to
 * be accessed. This annotation can be applied at the method level or at the
 * class level. If applied at the class level, it applies to all methods in the
 * class unless overridden by a method-level annotation.
 * 
 * Use {@link Permission} if you only need to specify a single permission for a
 * method or class.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermissions {

  Permission[] value();

  boolean allRequired() default true;
}
