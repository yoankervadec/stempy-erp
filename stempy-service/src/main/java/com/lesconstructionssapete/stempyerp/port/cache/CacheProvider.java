package com.lesconstructionssapete.stempyerp.port.cache;

import java.util.Map;
import java.util.Set;

public interface CacheProvider {

  void hset(String key, Map<String, String> values);

  Map<String, String> hgetAll(String key);

  void sadd(String key, String value);

  Set<String> smembers(String key);

  void delByPattern(String pattern);

  String getRaw(String key);

  void setRaw(String key, String value);

  void setex(String key, long seconds, String value);

  <T> T getObject(String key, Class<T> clazz);

  <T> void setObject(String key, T value);

}
