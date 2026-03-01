package com.lesconstructionssapete.stempyerp.core.config.redis;

import java.util.Map;
import java.util.Set;

public interface ConfigRedisClient {

  void hset(String key, Map<String, String> values);

  Map<String, String> hgetAll(String key);

  void sadd(String key, String value);

  Set<String> smembers(String key);

  void delByPattern(String pattern);

}
