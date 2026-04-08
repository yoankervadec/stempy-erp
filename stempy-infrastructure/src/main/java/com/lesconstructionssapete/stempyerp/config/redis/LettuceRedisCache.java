package com.lesconstructionssapete.stempyerp.config.redis;

import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesconstructionssapete.stempyerp.cache.RedisCache;
import com.lesconstructionssapete.stempyerp.util.JsonUtil;

import io.lettuce.core.KeyScanCursor;
import io.lettuce.core.ScanArgs;
import io.lettuce.core.ScanCursor;
import io.lettuce.core.api.sync.RedisCommands;

public class LettuceRedisCache implements RedisCache {

  private final RedisCommands<String, String> commands;

  public LettuceRedisCache(RedisCommands<String, String> commands) {
    this.commands = commands;
  }

  private static final ObjectMapper MAPPER = JsonUtil.mapper();

  @Override
  public void hset(String key, Map<String, String> values) {
    commands.hset(key, values);
  }

  @Override
  public Map<String, String> hgetAll(String key) {
    return commands.hgetall(key);
  }

  @Override
  public void sadd(String key, String value) {
    commands.sadd(key, value);
  }

  @Override
  public Set<String> smembers(String key) {
    return commands.smembers(key);
  }

  @Override
  public void delByPattern(String pattern) {

    if (!pattern.startsWith("config:")) {
      throw new IllegalArgumentException("Refusing to delete non-config keys: " + pattern);
    }

    ScanCursor cursor = ScanCursor.INITIAL;
    ScanArgs args = ScanArgs.Builder.matches(pattern).limit(100);

    do {
      KeyScanCursor<String> scan = commands.scan(cursor, args);

      if (!scan.getKeys().isEmpty()) {
        commands.del(scan.getKeys().toArray(new String[0]));
      }

      cursor = scan;

    } while (!cursor.isFinished());
  }

  @Override
  public String get(String key) {
    return commands.get(key);
  }

  @Override
  public void set(String key, String value) {
    commands.set(key, value);
  }

  @Override
  public void setex(String key, long seconds, String value) {
    commands.setex(key, seconds, value);
  }

  @Override
  public <T> T get(String key, Class<T> clazz) {
    String json = commands.get(key);
    if (json == null) {
      return null;
    }

    try {
      return MAPPER.readValue(json, clazz);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to deserialize value for key: " + key, e);
    }
  }

  @Override
  public <T> void set(String key, T value) {
    try {
      String json = MAPPER.writeValueAsString(value);
      commands.set(key, json);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to serialize value for key: " + key, e);
    }
  }
}
