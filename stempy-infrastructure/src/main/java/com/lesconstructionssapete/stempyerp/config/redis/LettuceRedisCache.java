package com.lesconstructionssapete.stempyerp.config.redis;

import java.util.Map;
import java.util.Set;

import com.lesconstructionssapete.stempyerp.cache.RedisCache;

import io.lettuce.core.KeyScanCursor;
import io.lettuce.core.ScanArgs;
import io.lettuce.core.ScanCursor;
import io.lettuce.core.api.sync.RedisCommands;

public class LettuceRedisCache implements RedisCache {

  private final RedisCommands<String, String> commands;

  public LettuceRedisCache(RedisCommands<String, String> commands) {
    this.commands = commands;
  }

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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void set(String key, String value) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setex(String key, long seconds, String value) {
    // TODO Auto-generated method stub

  }

}
