package com.lesconstructionssapete.stempyerp.core.config.db;

import java.time.Duration;

import io.github.cdimascio.dotenv.Dotenv;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;

public final class RedisProvider {

  private static RedisClient client;

  private RedisProvider() {
  }

  public static RedisClient getClient() {
    if (client == null) {
      Dotenv dotenv = Dotenv.load();

      RedisURI uri = RedisURI.builder()
          .withHost(dotenv.get("REDIS_HOST"))
          .withPort(Integer.parseInt(dotenv.get("REDIS_PORT")))
          .withTimeout(Duration.ofSeconds(2))
          .build();
      client = RedisClient.create(uri);
    }

    return client;
  }

}
