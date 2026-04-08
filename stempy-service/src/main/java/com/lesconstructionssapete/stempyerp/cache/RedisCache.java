package com.lesconstructionssapete.stempyerp.cache;

import java.util.Map;
import java.util.Set;

public interface RedisCache {

  void hset(String key, Map<String, String> values);

  Map<String, String> hgetAll(String key);

  void sadd(String key, String value);

  Set<String> smembers(String key);

  void delByPattern(String pattern);

  String get(String key);

  void set(String key, String value);

  void setex(String key, long seconds, String value);

  <T> T get(String key, Class<T> clazz);

  <T> void set(String key, T value);

}
