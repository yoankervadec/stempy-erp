package com.lesconstructionssapete.stempyerp.constant;

import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesconstructionssapete.stempyerp.config.redis.RedisProvider;
import com.lesconstructionssapete.stempyerp.domain.base.constant.EntityType;
import com.lesconstructionssapete.stempyerp.domain.base.constant.RetailCategory;
import com.lesconstructionssapete.stempyerp.service.base.constant.ConstantService;
import com.lesconstructionssapete.stempyerp.util.JsonUtil;

import io.lettuce.core.api.sync.RedisCommands;

/*
 * Caches configuration constants into memory.
 * This cache is intended to store constants that are frequently accessed and rarely changed, such as entity types, tax regions, order statuses, etc.
 * It uses Redis to store the cached constants, allowing for fast access and automatic expiration after a defined TTL (Time To Live). 
 */

public class ConstantCache {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantCache.class);
  private final ObjectMapper MAPPER = JsonUtil.mapper();

  private static final Duration CACHE_TTL = Duration.ofHours(12);

  private final RedisCommands<String, String> redis;
  private final ConstantService service;

  public ConstantCache(RedisProvider redisProvider, ConstantService service) {
    this.redis = redisProvider.getConnection().sync();
    this.service = service;
  }

  // Generic method to get or load a list of constants from Redis cache
  private <T> List<T> getOrLoad(
      String key,
      TypeReference<List<T>> type,
      Supplier<List<T>> loader) {

    try {
      String cached = redis.get(key);
      if (cached != null) {
        return MAPPER.readValue(cached, type);
      }

      List<T> loaded = loader.get();
      redis.setex(key, CACHE_TTL.getSeconds(), MAPPER.writeValueAsString(loaded));

      return loaded;
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to get or load cache for key: " + key, e);
    }
  }

  public void warmUp() throws SQLException {

    LOGGER.info("Loading and caching configuration constants...");

    getEntityTypes();
    LOGGER.info("\t ...Cached Entity Type");

    getRetailCategories();
    LOGGER.info("\t ...Cached Retail Categories");

    LOGGER.info("Finished caching configuration constants.\n");
  }

  public List<EntityType> getEntityTypes() {
    return getOrLoad(
        "constant:entity_types",
        new TypeReference<List<EntityType>>() {
        },
        () -> service.getEntityTypes());
  }

  public List<RetailCategory> getRetailCategories() {
    return getOrLoad(
        "constant:retail_categories",
        new TypeReference<List<RetailCategory>>() {
        },
        () -> service.getRetailCategories());
  }

}
