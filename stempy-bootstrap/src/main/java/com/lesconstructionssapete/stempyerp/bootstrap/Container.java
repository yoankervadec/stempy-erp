package com.lesconstructionssapete.stempyerp.bootstrap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Container {

  private final Map<Class<?>, Object> instances = new HashMap<>();
  private final Map<Class<?>, Class<?>> bindings = new HashMap<>();

  public <T> void bind(Class<T> abstraction, Class<? extends T> implementation) {
    bindings.put(abstraction, implementation);
  }

  public <T> void instance(Class<T> type, T object) {
    instances.put(type, object);
  }

  @SuppressWarnings("unchecked")
  public <T> T get(Class<T> type) {

    if (instances.containsKey(type)) {
      return (T) instances.get(type);
    }

    Class<?> implementation = bindings.getOrDefault(type, type);

    try {

      if (implementation.getConstructors().length != 1) {
        throw new RuntimeException("Class must have exactly one constructor");
      }

      Constructor<?> constructor = implementation.getDeclaredConstructors()[0];

      Class<?>[] paramTypes = constructor.getParameterTypes();
      Object[] params = new Object[paramTypes.length];

      for (int i = 0; i < paramTypes.length; i++) {
        params[i] = get(paramTypes[i]);
      }

      Object instance = constructor.newInstance(params);

      instances.put(type, instance);

      return (T) instance;

    } catch (
        IllegalAccessException | IllegalArgumentException | InstantiationException | SecurityException
        | InvocationTargetException e) {
      throw new RuntimeException("Failed to create " + type.getName(), e);
    }
  }

}
