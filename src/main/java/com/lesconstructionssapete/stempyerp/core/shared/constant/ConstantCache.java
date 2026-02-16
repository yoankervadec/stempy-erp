package com.lesconstructionssapete.stempyerp.core.shared.constant;

import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesconstructionssapete.stempyerp.core.config.redis.RedisProvider;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.CustomerOrderHeaderStatus;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.CustomerOrderLineStatus;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.EntityType;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.ItemEntryType;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.PosTransactionType;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.RetailCategory;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.RetailLocation;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.RoleAction;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.TaxRegion;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.UserAction;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.UserRole;
import com.lesconstructionssapete.stempyerp.core.service.base.constant.ConstantService;
import com.lesconstructionssapete.stempyerp.core.shared.util.JsonUtil;

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

    getTaxRegions();
    LOGGER.info("\t ...Cached Tax Region");

    getCustomerOrderHeaderStatuses();
    LOGGER.info("\t ...Cached Customer Order Header Status");

    getCustomerOrderLineStatuses();
    LOGGER.info("\t ...Cached Customer Order Line Status");

    getItemEntryTypes();
    LOGGER.info("\t ...Cached Item Entry Type");

    getPosTransactionTypes();
    LOGGER.info("\t ...Cached POS Transaction Type");

    getRetailCategories();
    LOGGER.info("\t ...Cached Retail Categories");

    getRetailLocations();
    LOGGER.info("\t ...Cached Retail Locations");

    getRoleActions();
    LOGGER.info("\t ...Cached Role Actions");

    getUserActions();
    LOGGER.info("\t ...Cached User Actions");

    getUserRoles();
    LOGGER.info("\t ...Cached User Roles");

    LOGGER.info("Finished caching configuration constants.\n");
  }

  public List<EntityType> getEntityTypes() {
    return getOrLoad(
        "constant:entity_types",
        new TypeReference<List<EntityType>>() {
        },
        () -> service.getEntityTypes());
  }

  public List<TaxRegion> getTaxRegions() {
    return getOrLoad(
        "constant:tax_regions",
        new TypeReference<List<TaxRegion>>() {
        },
        () -> service.getTaxRegions());
  }

  public List<CustomerOrderHeaderStatus> getCustomerOrderHeaderStatuses() {
    return getOrLoad(
        "constant:customer_order_header_statuses",
        new TypeReference<List<CustomerOrderHeaderStatus>>() {
        },
        () -> service.getCustomerOrderHeaderStatuses());
  }

  public List<CustomerOrderLineStatus> getCustomerOrderLineStatuses() {
    return getOrLoad(
        "constant:customer_order_line_statuses",
        new TypeReference<List<CustomerOrderLineStatus>>() {
        },
        () -> service.getCustomerOrderLineStatuses());
  }

  public List<ItemEntryType> getItemEntryTypes() {
    return getOrLoad(
        "constant:item_entry_types",
        new TypeReference<List<ItemEntryType>>() {
        },
        () -> service.getItemEntryTypes());
  }

  public List<PosTransactionType> getPosTransactionTypes() {
    return getOrLoad(
        "constant:pos_transaction_types",
        new TypeReference<List<PosTransactionType>>() {
        },
        () -> service.getPosTransactionTypes());
  }

  public List<RetailCategory> getRetailCategories() {
    return getOrLoad(
        "constant:retail_categories",
        new TypeReference<List<RetailCategory>>() {
        },
        () -> service.getRetailCategories());
  }

  public List<RetailLocation> getRetailLocations() {
    return getOrLoad(
        "constant:retail_locations",
        new TypeReference<List<RetailLocation>>() {
        },
        () -> service.getRetailLocations());
  }

  public List<RoleAction> getRoleActions() {
    return getOrLoad(
        "constant:role_actions",
        new TypeReference<List<RoleAction>>() {
        },
        () -> service.getRoleActions());
  }

  public List<UserAction> getUserActions() {
    return getOrLoad(
        "constant:user_actions",
        new TypeReference<List<UserAction>>() {
        },
        () -> service.getUserActions());
  }

  public List<UserRole> getUserRoles() {
    return getOrLoad(
        "constant:user_roles",
        new TypeReference<List<UserRole>>() {
        },
        () -> service.getUserRoles());
  }

}
