package com.lesconstructionssapete.stempyerp.core.config.redis;

import java.time.Duration;

import io.github.cdimascio.dotenv.Dotenv;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

public final class RedisProvider {

  private static RedisClient client;
  private static StatefulRedisConnection<String, String> connection;

  private RedisProvider() {
  }

  public static StatefulRedisConnection<String, String> getConnection() {
    if (connection == null) {
      Dotenv dotenv = Dotenv.load();

      RedisURI uri = RedisURI.builder()
          .withHost(dotenv.get("REDIS_HOST"))
          .withPort(Integer.parseInt(dotenv.get("REDIS_PORT")))
          .withTimeout(Duration.ofSeconds(2))
          .build();

      client = RedisClient.create(uri);
      connection = client.connect();
    }

    return connection;
  }

}
