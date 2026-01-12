package com.lesconstructionssapete.stempyerp.core.shared.constant;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesconstructionssapete.stempyerp.core.config.db.ConnectionPool;
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
import com.lesconstructionssapete.stempyerp.core.repository.base.constant.ConstantRepository;
import com.lesconstructionssapete.stempyerp.core.shared.util.JsonUtil;

import io.lettuce.core.api.sync.RedisCommands;

/*
 * Caches configuration constants into memory.
 */

public class ConstantCache {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantCache.class);
  private final ObjectMapper MAPPER = JsonUtil.mapper();
  private final RedisCommands<String, String> redis = RedisProvider.getConnection().sync();

  private static final Duration CACHE_TTL = Duration.ofHours(12);

  // ---- Singleton handling ----
  private static ConstantCache instance;

  private final ConstantRepository repository;

  // Cached constant lists
  private static List<EntityType> entityTypes;
  private static List<TaxRegion> taxRegions;
  private static List<CustomerOrderHeaderStatus> customerOrderHeaderStatuses;
  private static List<CustomerOrderLineStatus> customerOrderLineStatuses;
  private static List<ItemEntryType> itemEntryTypes;
  private static List<PosTransactionType> posTransactionTypes;
  private static List<RetailCategory> retailCategories;
  private static List<RetailLocation> retailLocations;
  private static List<RoleAction> roleActions;
  private static List<UserAction> userActions;
  private static List<UserRole> userRoles;

  // Create the singleton instance if it does not already exist.
  public static synchronized ConstantCache create(ConstantRepository repository) throws SQLException {
    if (instance == null) {
      instance = new ConstantCache(repository);
    }
    return instance;
  }

  // Get the singleton instance.
  public static ConstantCache getInstance() {
    if (instance == null) {
      throw new IllegalStateException("ConstantCache has not been initialized. Call create() first.");
    }
    return instance;
  }

  // Private constructor
  private ConstantCache(ConstantRepository repository) {
    this.repository = repository;
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
    } catch (Exception e) {
      throw new RuntimeException("Failed to get or load cache for key: " + key, e);
    }
  }

  public List<EntityType> getOrLoadEntityTypes() {
    return getOrLoad(
        "constant:entity_types",
        new TypeReference<List<EntityType>>() {
        },
        () -> {
          try {
            return repository.loadEntityTypes(ConnectionPool.getConnection());
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
  }

  public void warmUp(Connection con) throws SQLException {

    LOGGER.info("Loading and caching configuration constants...");

    entityTypes = repository.loadEntityTypes(con);
    LOGGER.info("\t ...Cached Entity Type");

    taxRegions = repository.loadTaxRegions(con);
    LOGGER.info("\t ...Cached Tax Region");

    customerOrderHeaderStatuses = repository.loadCustomerOrderHeaderStatuses(con);
    LOGGER.info("\t ...Cached Customer Order Header Status");

    customerOrderLineStatuses = repository.loadCustomerOrderLineStatuses(con);
    LOGGER.info("\t ...Cached Customer Order Line Status");

    itemEntryTypes = repository.loadItemEntryTypes(con);
    LOGGER.info("\t ...Cached Item Entry Type");

    posTransactionTypes = repository.loadPosTransactionTypes(con);
    LOGGER.info("\t ...Cached POS Transaction Type");

    retailCategories = repository.loadRetailCategories(con);
    LOGGER.info("\t ...Cached Retail Categories");

    retailLocations = repository.loadRetailLocations(con);
    LOGGER.info("\t ...Cached Retail Locations");

    roleActions = repository.loadRoleActions(con);
    LOGGER.info("\t ...Cached Role Actions");

    userActions = repository.loadUserActions(con);
    LOGGER.info("\t ...Cached User Actions");

    userRoles = repository.loadUserRoles(con);
    LOGGER.info("\t ...Cached User Roles");

    LOGGER.info("Finished caching configuration constants.\n");
  }

  public static List<EntityType> getEntityTypes() {
    return entityTypes;
  }

  public static List<TaxRegion> getTaxRegions() {
    return taxRegions;
  }

  public static List<CustomerOrderHeaderStatus> getCustomerOrderHeaderStatuses() {
    return customerOrderHeaderStatuses;
  }

  public static List<CustomerOrderLineStatus> getCustomerOrderLineStatuses() {
    return customerOrderLineStatuses;
  }

  public static List<ItemEntryType> getItemEntryTypes() {
    return itemEntryTypes;
  }

  public static List<PosTransactionType> getPosTransactionTypes() {
    return posTransactionTypes;
  }

  public static List<RetailCategory> getRetailCategories() {
    return retailCategories;
  }

  public static List<RetailLocation> getRetailLocations() {
    return retailLocations;
  }

  public static List<RoleAction> getRoleActions() {
    return roleActions;
  }

  public static List<UserAction> getUserActions() {
    return userActions;
  }

  public static List<UserRole> getUserRoles() {
    return userRoles;
  }

}
