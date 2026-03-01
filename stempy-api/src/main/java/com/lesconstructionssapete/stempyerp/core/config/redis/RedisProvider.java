package com.lesconstructionssapete.stempyerp.core.config.redis;

import java.time.Duration;

import io.github.cdimascio.dotenv.Dotenv;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

public final class RedisProvider {

  private final StatefulRedisConnection<String, String> connection;

  public RedisProvider() {
    Dotenv dotenv = Dotenv.load();

    RedisURI uri = RedisURI.builder()
        .withHost(dotenv.get("REDIS_HOST"))
        .withPort(Integer.parseInt(dotenv.get("REDIS_PORT")))
        .withTimeout(Duration.ofSeconds(2))
        .build();

    RedisClient client = RedisClient.create(uri);
    this.connection = client.connect();
  }

  public StatefulRedisConnection<String, String> getConnection() {
    return connection;
  }

}
