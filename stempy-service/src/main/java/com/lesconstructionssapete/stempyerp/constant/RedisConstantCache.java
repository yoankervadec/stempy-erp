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
import com.lesconstructionssapete.stempyerp.cache.RedisCache;
import com.lesconstructionssapete.stempyerp.domain.constant.DomainEntityType;
import com.lesconstructionssapete.stempyerp.domain.constant.PaymentMethod;
import com.lesconstructionssapete.stempyerp.domain.constant.RetailCategory;
import com.lesconstructionssapete.stempyerp.domain.constant.TaxGroup;
import com.lesconstructionssapete.stempyerp.service.constant.ConstantService;
import com.lesconstructionssapete.stempyerp.util.JsonUtil;

/*
 * Caches configuration constants into memory.
 * This cache is intended to store constants that are frequently accessed and rarely changed, such as entity types, tax regions, order statuses, etc.
 * It uses Redis to store the cached constants, allowing for fast access and automatic expiration after a defined TTL (Time To Live). 
 */

public class RedisConstantCache implements ConstantCache {

  private static final Logger LOGGER = LoggerFactory.getLogger(RedisConstantCache.class);
  private final ObjectMapper MAPPER = JsonUtil.mapper();

  private static final Duration CACHE_TTL = Duration.ofHours(12);

  private final RedisCache redis;
  private final ConstantService service;

  public RedisConstantCache(RedisCache redis, ConstantService service) {
    this.redis = redis;
    this.service = service;
  }

  // Generic method to get or load a list of constants from Redis cache
  private <T> List<T> getOrLoad(
      String key,
      TypeReference<List<T>> type,
      Supplier<List<T>> loader) {

    try {
      String cached = redis.get(key);
      if (cached != null && !cached.isBlank()) {
        return MAPPER.readValue(cached, type);
      }

      List<T> loaded = loader.get();
      redis.setex(key, CACHE_TTL.getSeconds(), MAPPER.writeValueAsString(loaded));

      return loaded;
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to get or load cache for key: " + key, e);
    }
  }

  @Override
  public void warmUp() throws SQLException {

    LOGGER.info("Loading and caching configuration constants...");

    getDomainEntityTypes();
    LOGGER.info("\t ...Cached Entity Type");

    getRetailCategories();
    LOGGER.info("\t ...Cached Retail Categories");

    getTaxGroups();
    LOGGER.info("\t ...Cached Tax Groups");

    getPaymentMethods();
    LOGGER.info("\t ...Cached Payment Methods");

    LOGGER.info("Finished caching configuration constants.\n");
  }

  @Override
  public List<DomainEntityType> getDomainEntityTypes() {
    return getOrLoad(
        "constant:entity_types",
        new TypeReference<List<DomainEntityType>>() {
        },
        () -> service.getDomainEntityTypes());
  }

  @Override
  public List<RetailCategory> getRetailCategories() {
    return getOrLoad(
        "constant:retail_categories",
        new TypeReference<List<RetailCategory>>() {
        },
        () -> service.getRetailCategories());
  }

  @Override
  public List<TaxGroup> getTaxGroups() {
    return getOrLoad(
        "constant:tax_groups",
        new TypeReference<List<TaxGroup>>() {
        },
        () -> service.getTaxGroups());
  }

  @Override
  public List<PaymentMethod> getPaymentMethods() {
    return getOrLoad(
        "constant:payment_methods",
        new TypeReference<List<PaymentMethod>>() {
        },
        () -> service.getPaymentMethods());
  }

}
